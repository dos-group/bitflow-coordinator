    import { createCurrentTimeFormatted, formatISODate } from '../../utils';
    import * as d3 from "d3"
    import Vue from "vue"

    export default {

        name: 'Editor',

        data() {
            const blobs = true;
            const steptest = {};
            const paramWithval = {};
            const projectId = this.$router.history.current.fullPath.split('/')[2];
            const pipelineId = this.$router.history.current.fullPath.split('/')[4];
            const pipelineName = '';
            const source= '';
            const destination= '';
            let countNumbers = 0;
            const allNodes = [];
            const coordinatesOfNodes = [];
            const allSteps = [];
            const allLines = [];
            const parameter ='';
            let modalErrorMessage = "";
            return {allSteps, allNodes, blobs, allLines, coordinatesOfNodes, countNumbers, projectId, pipelineId,destination,source,modalErrorMessage,pipelineName,parameter,steptest, paramWithval}
        },
        async created() {
            let here = this;
            let highestNumber = 0;
            try {
            // get the available capabilities from the backend and save them 
                const capabilities = await this.$backendCli.getCapabilities(1);
                capabilities.data.forEach(function (capa){
                    here.allSteps.push({
                        "Number": "",
                        "AgentId": "",
                        "Typ": "operation",
                        "Content": capa.Name,
                        "Params": capa.RequiredParams,
                        "Successors": []
                    })
            });
            // get an existing pipeline from the backend if its available 
                const pipeline = await this.$backendCli.getPipeline(this.projectId ,this.pipelineId );
                this.pipelineName = pipeline.data.Name
                pipeline.data.PipelineSteps.forEach(function (step) {
                    here.allNodes.push(step);
                    // update the step number so we start with the highest number when adding new steps
                    if (step.Number > highestNumber){
                      highestNumber= step.Number;
                    }
                });
            // arrange the nodes if all steps are loaded
              here.$nextTick(() => this.ArrangeNodes());
        }
            catch (e) {
                this.$notifyError(e);
            }
            this.countNumbers = highestNumber+1;
        },
        methods: {
          ArrangeNodes: function () {
            let here = this;
            // fill the squares with color 
            const nodes = document.getElementsByClassName('square');
            Array.from(nodes).forEach(function (node) {
              node.childNodes[0].style.fill = here.getColor(node.childNodes[8].textContent.toString());
            });

            // arranges the steps to be visual accessable
            let arrangeNodes = function (_callback) {
              let nodeLevel = 0;

              here.allNodes.forEach(function (node) {
                nodeLevel += 1;
                let yLevel = 0;
                let endLevel= 1;


                if (node.Successors.length == 0) {
                  const squares = document.getElementsByClassName('square');
                  Array.from(squares).forEach(function (square) {
                    let squareId = square.childNodes[6].textContent.match(/\d+/)[0];
                    if (squareId == node.Number) {
                      d3.select(square).attr('transform', 'translate(' + (nodeLevel * 50) + ',' + ((endLevel * 50) + 7.5)+ ')');
                    }
                  })
                  here.coordinatesOfNodes.push({
                    "Number": node.Number,
                    "coords": [(nodeLevel * 50) + 20, (endLevel * 50) + 15]
                  });
                }

                node.Successors.forEach(function (succer) {
                  yLevel += 1;
                  let nodeS = false;
                  const Number = node.Number;
                  here.coordinatesOfNodes.forEach(function (c) {
                    if (c.Number == Number) {
                      nodeS = true;
                    }
                  });
                  if (!nodeS) {
                    const squares = document.getElementsByClassName('square');
                    Array.from(squares).forEach(function (square) {
                      let squareId = square.childNodes[6].textContent.match(/\d+/)[0];
                      if (squareId == node.Number) {
                        let x = (nodeLevel * 50) + 10;
                        let y = (yLevel * 50) + 7.5;
                        d3.select(square).attr('transform', 'translate(' + x + ',' + y + ')');
                      }
                    });

                    // we need to save the position of the steps in order to keep track of them
                    here.coordinatesOfNodes.push({
                      "Number": Number,
                      "coords": [(nodeLevel * 50) + 20, (yLevel * 50) + 15]
                    });
                  }
                  here.allLines.push({"start": node.Number, "end": succer})
                })
              });
              _callback();
            };

            //draw the connection between the steps 
            let paintLines = function () {
              here.allLines.forEach(function (line) {
                here.drawLine(line.start, line.end)
              })
              here.updateLines();
              here.updateNodes();
            }

            arrangeNodes(() => paintLines());

            //console.log(this.allNodes);
          },
          //function to delete existing connections
            deleteLine: function (start, end) {
                let here = this;

                // the data of the steps has to be changed
                here.allNodes.forEach(function (current) {
                    current.Successors.forEach(function(succ) {
                        if (succ == end){
                        var posSuccer = current.Successors.indexOf(succ);
                        current.Successors.splice(posSuccer,1)
                    }
                    })

                })

                //actual deleting now
                here.allLines.forEach(function (line) {
                    if (line.start == start && line.end == end) {
                        here.allLines.splice(here.allLines.indexOf(line), 1);
                        d3.select("#line" + line.start + line.end).remove();
                        d3.selectAll(".markerId" + line.start + line.end).remove();
                        d3.selectAll("#delete" + line.start + line.end).remove();
                    }
                });
            },
            // function to update the position of set nodes if they are changed
            checkPos: function () {
                const vm = this;
                const squares = document.getElementsByClassName('square');
                Array.from(squares).forEach(function (n) {

                    const str = d3.select(n).attr('transform')
                    const res = str.split("(")[1].split(",");
                    const posX = parseInt(res[0]) + 10;
                    const posY = parseInt(res[1].split(")")[0]) + 7.5

                    let node = false;
                    const Number = n.childNodes[6].textContent.match(/\d+/)[0];
                    vm.coordinatesOfNodes.forEach(function (c) {
                        if (c.Number == Number) {
                            node = true;
                            vm.coordinatesOfNodes[vm.coordinatesOfNodes.findIndex(k => k === c)].coords = [posX, posY];
                        }
                    });
                    if (!node) {
                        vm.coordinatesOfNodes.push({"Number": Number, "coords": [posX, posY]});
                    }
                })
            }
            ,
            // draws a connection between a start and an end node
            drawLine: function (start, end) {
                const here = this;
                const svg = d3.select("svg");
                var startNode = "empty";
                var endNode = "empty";
                here.coordinatesOfNodes.forEach(function (node) {
                    if (node.Number == start) {
                        startNode = node.coords
                    }
                    if (node.Number == end) {
                        endNode = node.coords
                    }
                })

                //build a line as svg elements 
                if (startNode !== "empty" && endNode !== "empty") {
                    svg.select(".lines").append("path")
                        .attr('d', 'M' + startNode[0] + ' , ' + startNode[1] + ' L ' + endNode[0] + ' , ' + endNode[1])
                        .attr('class', 'normalLine')
                        .attr('id', 'line' + start + end)


                    svg.select(".markers").append("text")
                        .attr("class", "markerId" + start + end)
                        .append("textPath")
                        .attr('xlink:href', '#line' + start + end)
                        .attr('startOffset', '50%')
                        .attr('id', 'delete' + start + end)
                        .text("X")

                    //add the delete possibility
                    var str = 'delete' + start + end
                    document.getElementById(str).onclick = function () {
                        here.deleteLine(start, end)
                    };

                    // paint the markers to display a line
                    for (let i = 0; i <= 10; i++) {
                        if (i == 5) {
                            continue
                        }
                        svg.select(".markers")
                            .append("text")
                            .attr("class", "markerId" + start + end)
                            .append("textPath")
                            .attr('xlink:href', '#line' + start + end)
                            .attr('startOffset', (i * 10) + '%')
                            .text("➤")

                    }

                }
            }
            ,
            //updates the line if a node which is connected to a line is moved
            changeLine: function (number) {
                const here = this;
                const svg = d3.select("svg");

                let changedCoords = [];

                here.coordinatesOfNodes.forEach(function (node) {
                    if (node.Number == number) {
                        changedCoords = node.coords
                    }
                })

                this.allLines.forEach(function (line) {

                        if (number == line.start) {
                            let coorse = 0;
                            //get the other node
                            here.coordinatesOfNodes.forEach(function (node) {
                                if (node.Number == line.end) {
                                    coorse = node.coords
                                }
                            })
                            svg.select("#line" + line.start + line.end).attr('d', 'M' + changedCoords[0] + ' , ' + changedCoords[1] + ' L ' + coorse[0] + ' , ' + coorse[1])
                        } else if (number == line.end) {
                            let coors = 0;
                            //get the other node
                            here.coordinatesOfNodes.forEach(function (node) {
                                if (node.Number == line.start) {
                                    coors = node.coords
                                }
                            })
                            svg.select("#line" + line.start + line.end).attr('d', 'M' + coors[0] + ' , ' + coors[1] + ' L ' + changedCoords[0] + ' , ' + changedCoords[1])
                        }
                    }
                )
            }
            ,
            //drag mechanics for nodes are added 
            updateNodes: function () {
                const here = this;
                const svg = d3.select("svg");

                svg.select(".recs").selectAll("g")
                    .call(d3.drag()
                        .on("start", dragstarted)
                        .on("drag", dragged)
                        .on("end", dragended));


                function dragstarted(d) {
                    d3.select(this).raise().classed("active", true);
                }

                function dragged(d) {
                    const Number = d3.select(this).selectAll("text")._groups[0][2].innerHTML.match(/\d+/)[0];
                    here.checkPos();
                    setTimeout(here.changeLine(Number), 100)
                    d3.select(this).attr('transform', 'translate(' + (d3.event.x - 10) + ',' + (d3.event.y - 7) + ')');
                }

                function dragended(d) {
                    const Number = d3.select(this).selectAll("text")._groups[0][2].innerHTML.match(/\d+/)[0];
                    here.checkPos();
                    setTimeout(here.changeLine(Number), 100)
                    d3.select(this).classed("active", false);
                }
            }
            ,
            //drag mechanics for lines are added 
            updateLines: function () {
                let here = this;
                const svg = d3.select("svg");

                svg.select(".recs").selectAll("circle")
                    .call(d3.drag()
                        .on("start", dragstarted)
                        .on("drag", dragged)
                        .on("end", dragended));


                function dragstarted(d) {
                    d3.select(".dragline").attr('hidden', null);
                    const str = d3.select(this.parentNode).attr('transform')
                    const res = str.split("(")[1].split(",");
                    const start = parseInt(res[0]) + 10;
                    const end = parseInt(res[1].split(")")[0]) + 7.5
                    d3.select(".dragline").attr('d', 'm' + start + "," + end + 'l' + d3.event.x + ',' + d3.event.y);
                    d3.select(this).raise().classed("active", true);
                }

                //while beeing dragged paint a line to show the user where goes with the line
                function dragged(d) {
                    const str = d3.select(this.parentNode).attr('transform')
                    const res = str.split("(")[1].split(",");
                    const start = parseInt(res[0]) + 10;
                    const end = parseInt(res[1].split(")")[0]) + 7.5
                    d3.select(".dragline").attr('d', 'm' + start + "," + end + 'l' + d3.event.x + ',' + d3.event.y);
                }

                // if a line is dragged to a node we build a connection between the two nodes
                function dragended(d) {
                    const numberOfNode = d3.select(this.parentNode).selectAll("text")._groups[0][2].innerHTML.match(/\d+/)[0];
                    const str = d3.select(this.parentNode).attr('transform')
                    const res = str.split("(")[1].split(",");
                    const start = parseInt(res[0]);
                    const end = parseInt(res[1].split(")")[0])
                    here.coordinatesOfNodes.forEach(function (node) {
                        if ((d3.event.x + start <= (node.coords[0] + 15) && d3.event.x + start >= (node.coords[0] - 15)) && (d3.event.y + end <= (node.coords[1] + 15) && d3.event.y + end >= (node.coords[1] - 15))) {
                            const coor = {"start": numberOfNode, "end": node.Number}
                            let FutureNode = node.Number;
                            var isIn = true;
                            here.allLines.forEach(function (line) {
                                if (line.start == numberOfNode && line.end == node.Number) {
                                    isIn = false;
                                }
                            })
                            if (isIn) {
                                here.allNodes.forEach(function (nod) {
                                    if (nod.Number == numberOfNode){
                                        nod.Successors.push(FutureNode)
                                    }
                                })
                                here.allLines.push(coor)
                                setTimeout(here.drawLine(numberOfNode, node.Number), 100)
                            }
                        }
                    })
                    d3.select(".dragline").attr('hidden', 'hidden');
                    d3.select(this).classed("active", false);
                }

            }
            ,
            //deletes a node from the pipeline
            deleteNode: function (node) {
                let here = this;
                let found = false

                //delete coordinates of nodes
                here.coordinatesOfNodes.forEach(function (cnode) {
                    if (cnode.Number == node.Number) {
                        here.coordinatesOfNodes.splice(here.coordinatesOfNodes.indexOf(cnode), 1)
                        found = true;
                    }
                });

                var index = this.allNodes.indexOf(node);

                //delete from pipeline array
                here.allNodes.forEach(function (current) {
                    current.Successors.forEach(function(succ) {
                        if (succ == node.Number){
                        var posSuccer = current.Successors.indexOf(succ);
                        current.Successors.splice(posSuccer,1)
                    }
                    })

                })

                here.allNodes.splice(index, 1)

                //delete all connections of this node
                var length = here.allLines.length;
                for (var i = length - 1; i >= 0; i--) {
                    if (here.allLines[i].end == node.Number || here.allLines[i].start == node.Number) {
                        var line = here.allLines[i];
                        d3.select("#line" + line.start + line.end).remove();
                        d3.selectAll(".markerId" + line.start + line.end).remove();
                        d3.selectAll("#delete" + line.start + line.end).remove();
                        here.allLines.splice(here.allLines.indexOf(line), 1);
                    }
                }
            }
            ,
            //add a new node to the pipeline
            createNode: function (nodeId) {

                const here = this;

                // start and end nodes are special cases
                if (nodeId === "start") {
                    this.countNumbers += 1;
                    const startNode = {
                        "Number": this.countNumbers,
                        "Typ": "source",
                        "Content": this.source,
                        "Params": [],
                        "Successors": []
                    };

                    this.countNumbers += 1;
                    this.allNodes.push(startNode);

                } else if (nodeId === "end") {
                    this.countNumbers += 1;
                    const endNode = {
                        "Number": this.countNumbers,
                        "Typ": "sink",
                        "Content": this.destination,
                        "Params": [],
                        "Successors": []
                    };

                    this.countNumbers += 1;
                    this.allNodes.push(endNode);

                } else {
                    const index = this.allSteps.findIndex(node => node.Content === nodeId.Content);
                    const changingNode = this.allSteps.slice(index, index + 1)[0];
                    //create a hard copy of node
                    const newNode = Object.assign({}, changingNode);
                    newNode.Number = this.countNumbers;
                    this.countNumbers += 1;

                    newNode.Params = [];
                    newNode.Params.push(this.paramWithval);
                    this.paramWithval = {};

                    this.allNodes.push(newNode);
                }

                //add drag mechanics, coordinates and color. 
                setTimeout(this.updateNodes, 100)
                setTimeout(this.updateLines, 100)
                setTimeout(this.checkPos, 100)
                setTimeout(this.colorGraph, 10)

            },
            //color the nodes in their perspective color
            colorGraph: function () {
                let here = this;

                const nodes = document.getElementsByClassName('square');
                Array.from(nodes).forEach(function (node) {
                    if (node.childNodes[8].textContent.includes("sink")) {
                        node.childNodes[0].style.fill = "rgba(69, 131, 174,1)";
                    } else if (node.childNodes[8].textContent.includes("source")) {
                        node.childNodes[0].style.fill = "rgba(255, 135, 91,1)";
                    }
                    else {
                        node.childNodes[0].style.fill = "rgba(152, 231, 82,1)";
                    }
                })
            },
            //get color of node
            getColor: function (typ) {
                if (typ.indexOf("source") != -1)
                    return 'rgba(255, 135, 91,1)'
                if (typ.indexOf("sink") != -1)
                    return 'rgba(69, 131, 174,1)'
                else
                    return 'rgba(152, 231, 82,1)'
            },
            // save a pipeline to the backend
            updatePipeline: async function () {

              function isEmpty(obj) {
                for(var key in obj) {
                  if(obj.hasOwnProperty(key))
                    return false;
                }
                return true;
              }

                {
                  this.allNodes.forEach(function (Node) {
                    if(isEmpty(Node.Params[0])){
                      Node.Params=[];
                    }
                  });
                    try {
                        const pipeline = {
                            "ID": this.$router.history.current.fullPath.split('/')[4],
                            "LastChanged": createCurrentTimeFormatted(),
                            "Name":this.pipelineName,
                            "PipelineSteps": this.allNodes
                        };
                        const resp = await this.$backendCli.updatePipeline(this.projectId, pipeline);
                        if (resp.statusText == "OK")
                        {
                            this.$notifyInfo("Pipeline successfully Saved.")
                        }
                        return true;
                    } catch (e) {
                        this.$notifyError(e);
                    }
                }
            },
            //save and start a created pipeline
            saveandstart: async function () {
                const done = await this.updatePipeline();
                if (done ) {
                    //console.log("Pipeline updated");
                    await this.startPipeline();
                }
            },
            //start a created pipeline
            startPipeline: async function () {
                try {
                   // console.log(this.pipelineId)
                    const resp = await this.$backendCli.startPipeline(this.projectId, this.pipelineId);
                  this.$notifyInfo("Pipeline started")
                  // console.log(resp);
                } catch (e) {
                    // TODO: handle error:
                    this.$notifyInfo("Pipeline started")
                }
            },
            clearModal: function(){
                this.source = "";
                this.destination = "";
                this.paramWithval = {};
            },
            showModalErrorMessage: function(messageOrError){
                this.modalErrorMessage = messageOrError.message || messageOrError.errorMessage || messageOrError;
            },
        },
        mounted: function () {

            const here = this;

            const svg = d3.select("svg");

            //add zoom mechanic to editor
            const zoomed = function () {
                d3.select(".wholeGraph")
                    .attr('transform', 'translate(' + d3.event.transform.x + ',' + d3.event.transform.y + ') scale(' + d3.event.transform.k + ')');
            };

            const dragSvg = d3.zoom()
                .on("zoom", function () {
                    zoomed.call(svg);
                    setTimeout(here.checkPos(), 100)
                    return true;
                });

            svg.call(dragSvg).on("dblclick.zoom", null);

            this.updateNodes();
            this.updateLines();
        },

    }