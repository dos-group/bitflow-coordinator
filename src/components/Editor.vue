<!--suppress VueDuplicateTag -->

<!--
    TODO: Size of text in recs might be too big (line breaks!)
-->
<template>
    <div class="page-content" style="margin-bottom: 5px">
        <div class="row">
            <h4>Editor for pipeline: </h4>
        </div>
        <div class="row viewContainer">
            <div class="contain list-group col-lg-2 col-md-auto col-sm-auto">
                <div v-on:click="createNode('start')" class="static step start">
                    <div class="card-block">
                        <h5 class="card-title">Start of the pipeline </h5>
                        <p class="card-text">Source : </p>
                    </div>
                </div>
                <div v-on:click="createNode('end')" class="static step end">
                    <div class="card-block">
                        <h5 class="card-title">End of the pipeline</h5>
                        <p class="card-text">Results :</p>
                    </div>
                </div>
                <div v-on:click="createNode(step.ID)" class="card step" v-for="step in allSteps">
                    <div class="card-block">
                        <h5 class="card-title">Step Id : {{step.ID}}</h5>
                        <p class="card-text">Step Number : {{step.Number}}</p>
                        <p class="card-text">Content : {{step.Content}}</p>
                        <p class="card-text">Typ : {{step.Typ}}</p>
                    </div>
                </div>
            </div>
            <div class="svg-container col-lg-10 col-md-auto col-sm-auto">
                <svg preserveAspectRatio="xMidYMid meet" class="svg-content" viewBox="0 0 200 100">
                    <defs>
                        <marker id="Triangle" viewBox="0 0 10 10" refX="1" refY="5"
                                markerWidth="4" markerHeight="4" orient="auto">
                            <path d="M 0 0 L 10 5 L 0 10 z"></path>
                        </marker>
                    </defs>

                    <g class="wholeGraph">
                        <path hidden d="m0,0 l 0,0" class="dragline" style="marker-end: url(#Triangle)"></path>
                        <g class="lines">
                        </g>
                        <g id="markers" class="markers"></g>
                        <g class="recs">
                            <g class="square" transform="translate(70,50)" v-for="node in allNodes" :key="node.Number">
                                <rect width="20" height="15" rx="1" ry="1" style="opacity: 1"></rect>
                                <text font-family="FontAwesome" font-size="0.2em" dx="16" v-on:click="deleteNode(node)"
                                      dy="4">
                                    &#xf1f8;
                                </text>
                                <text class="IDField" dx="1" dy="3" font-size="1.5px">
                                    ID : {{node.ID}}
                                </text>
                                <text dx="1" dy="6" font-size="1.5px">
                                    Number : {{node.Number}}
                                </text>
                                <text dx="1" dy="9" font-size="1.5px">
                                    Typ : {{node.Typ}}
                                </text>
                                <text dx="1" dy="12" font-size="1.5px">
                                    Content : {{node.Content}}
                                </text>
                                <circle v-if="blobs" r="1" cx="21" cy="7.5"></circle>
                            </g>
                        </g>
                    </g>
                </svg>
            </div>
        </div>
    </div>
</template>
<script>
  import * as d3 from "d3"
  import Vue from "vue"

  export default {

    name: 'Editor',
    data() {

      /*    TODO : let the blobs disappear when not needed
            v-on:mouseout="blobs = !blobs" v-on:mouseover="blobs = !blobs"*/
      const blobs = true;

      let countNumbers = 0;

      const allNodes = [];

      const coordinatesOfNodes = [];

      const colorsOfNodes = [];

      const allSteps = [{
        "ID": 1,
        "Number": 0,
        "Typ": "source",
        "Content": "127.0.0.1:5555",
        "Params": [],
        "Successors": []
      }, {
        "ID": 2,
        "Number": 1,
        "Typ": "source",
        "Content": "127.0.0.1:5555",
        "Params": [],
        "Successors": []
      }, {
        "ID": 3,
        "Number": 2,
        "Typ": "source",
        "Content": "127.0.0.1:5555",
        "Params": [],
        "Successors": []
      }, {
        "ID": 4,
        "Number": 3,
        "Typ": "source",
        "Content": "127.0.0.1:5555",
        "Params": [],
        "Successors": []
      }, {
        "ID": 5,
        "Number": 4,
        "Typ": "source",
        "Content": "127.0.0.1:5555",
        "Params": [],
        "Successors": []
      }
      ];

      const allLines = [];

      return {allSteps, allNodes, blobs, allLines, coordinatesOfNodes, countNumbers, colorsOfNodes}
    },
    methods: {
      deleteLine: function (start, end) {
        //TODO delete successor in model!!
        let here = this;
        here.allLines.forEach(function (line) {
          if (line.start == start && line.end == end) {
            here.allLines.splice(here.allLines.indexOf(line), 1);
            d3.select("#line" + line.start + line.end).remove();
            d3.selectAll(".markerId" + line.start + line.end).remove();
            d3.selectAll("#delete" + line.start + line.end).remove();
          }
        });
      },
      checkPos: function () {
        const vm = this;
        const squares = document.getElementsByClassName('square');
        Array.from(squares).forEach(function (n) {

          const str = d3.select(n).attr('transform')
          const res = str.split("(")[1].split(",");
          const posX = parseInt(res[0]) + 10;
          const posY = parseInt(res[1].split(")")[0]) + 7.5

          /*const posX = n.getBoundingClientRect().x;
          const posY = n.getBoundingClientRect().y;*/
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
        if (startNode !== "empty" && endNode !== "empty") {
          //TODO let the marker be on the right position
          svg.select(".lines").append("path")
            .attr('d', 'M' + startNode[0] + ' , ' + startNode[1] + ' L ' + endNode[0] + ' , ' + endNode[1])
            .attr('class', 'normalLine')
            //.attr('marker-mid', 'url(#Triangle)')
            // .attr('style', 'marker-end: url(#Triangle);marker-mid: url(#Triangle)')
            .attr('id', 'line' + start + end)


          svg.select(".markers").append("text")
            .attr("class", "markerId" + start + end)
            .append("textPath")
            .attr('xlink:href', '#line' + start + end)
            .attr('startOffset', '50%')
            .attr('id', 'delete' + start + end)
            .text("X")

          var str = 'delete' + start + end
          document.getElementById(str).onclick = function () {
            here.deleteLine(start, end)
          };

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

        function dragged(d) {
          const str = d3.select(this.parentNode).attr('transform')
          const res = str.split("(")[1].split(",");
          const start = parseInt(res[0]) + 10;
          const end = parseInt(res[1].split(")")[0]) + 7.5
          d3.select(".dragline").attr('d', 'm' + start + "," + end + 'l' + d3.event.x + ',' + d3.event.y);
        }

        function dragended(d) {
          const numberOfNode = d3.select(this.parentNode).selectAll("text")._groups[0][2].innerHTML.match(/\d+/)[0];
          const str = d3.select(this.parentNode).attr('transform')
          const res = str.split("(")[1].split(",");
          const start = parseInt(res[0]);
          const end = parseInt(res[1].split(")")[0])
          here.coordinatesOfNodes.forEach(function (node) {
            if ((d3.event.x + start <= (node.coords[0] + 15) && d3.event.x + start >= (node.coords[0] - 15)) && (d3.event.y + end <= (node.coords[1] + 15) && d3.event.y + end >= (node.coords[1] - 15))) {
              const coor = {"start": numberOfNode, "end": node.Number}
              var isIn = true;
              here.allLines.forEach(function (line) {
                if (line.start == numberOfNode && line.end == node.Number) {
                  isIn = false;
                }
              })
              if (isIn) {
                here.allLines.push(coor)
                setTimeout(here.drawLine(numberOfNode, node.Number), 100)
                //here.coordinatesOfNodes[vm.coordinatesOfNodes.findIndex(k => k === c)].start = [posX, posY];
              }
            }
          })
          d3.select(".dragline").attr('hidden', 'hidden');
          d3.select(this).classed("active", false);
        }

      }
      ,

      deleteNode: function (node) {
        let here = this;
        let found = false

        here.coordinatesOfNodes.forEach(function (cnode) {
          if (cnode.Number == node.Number) {
            here.coordinatesOfNodes.splice(here.coordinatesOfNodes.indexOf(cnode), 1)
            found = true;
          }
        });

        var index = this.allNodes.indexOf(node);
        here.allNodes.splice(index, 1)

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

      createNode: function (nodeId) {

        const here = this;

        if (nodeId === "start") {

          const startNode = {
            "ID": "START",
            "Number": this.countNumbers,
            "Typ": null,
            "Content": null,
            "Params": [],
            "Successors": []
          };

          this.countNumbers += 1;
          this.allNodes.push(startNode);

        } else if (nodeId === "end") {

          const endNode = {
            "ID": "END",
            "Number": this.countNumbers,
            "Typ": null,
            "Content": null,
            "Params": [],
            "Successors": null
          };

          this.countNumbers += 1;

          this.allNodes.push(endNode);

        } else {

          const index = this.allSteps.findIndex(node => node.ID === nodeId);
          const changingNode = this.allSteps.slice(index, index + 1)[0];
          const newNode = Object.assign({}, changingNode);
          newNode.Number = this.countNumbers;
          this.countNumbers += 1;
          this.allNodes.push(newNode);
        }


        setTimeout(this.updateNodes, 100)
        setTimeout(this.updateLines, 100)
        setTimeout(this.checkPos, 100)
        setTimeout(this.colorGraph, 10)

      },
      colorGraph: function () {
        let here = this;

        const nodes = document.getElementsByClassName('square');
        Array.from(nodes).forEach(function (node) {
          console.log()
          if (node.childNodes[4].textContent.includes("END") || node.childNodes[4].textContent.includes("START")) {
            node.childNodes[0].style.fill = "rgba(255,0,0,1)";
          } else {
            var nodeId = node.childNodes[4].textContent.match(/\d+/)[0];
            here.colorsOfNodes.forEach(function (color) {
              if (color.id == nodeId) {
                node.childNodes[0].style.fill = color.color;
              }
            })
          }
        })
      }
    }
    ,
    mounted: function () {

      const here = this;


      const steps = document.getElementsByClassName('card step');
      Array.from(steps).forEach(function (step) {
        var color = random_rgba();
        step.style.backgroundColor = color;
        let id = step.childNodes[0].textContent.match(/\d+/)[0];
        here.colorsOfNodes.push({"id": id, "color": color})
      })

      function random_rgba() {
        var o = Math.round, r = Math.random, s = 400;

        //return 'rgba(' + o(r() * s) + ',0,0,1)';
        return 'rgba(' + o(r() * s) + ',' + o(r() * s) + ',' + o(r() * s) + ',1)';
      }


      const svg = d3.select("svg");

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
    }
  }
</script>
<style>

    .row.viewContainer {
        border: 2px solid rgb(66, 185, 131);
        border-radius: 0.5em;
        padding: 5px;
    }

    .contain {
        max-height: 600px !important;
    }

    .active rect {
        stroke: #000;
        stroke-width: 0.3px;
    }

    .svg-container {
        max-height: 600px !important;
        display: inline-block;
        position: relative;
        width: 100%;
        vertical-align: top;
        overflow: hidden;
    }

    .svg-content {
        display: inline-block;
        position: absolute;
        top: 0;
        left: 0;
        border-left: 2px solid rgb(66, 185, 131);
        margin-left: 15px;
        padding-bottom: 25px;
        padding-right: 30px;
    }

    .card.step {
        margin-bottom: 5px;
        padding: 10px;
    }

    .static.step.start {
        border-radius: 0.2em;
        margin-bottom: 5px;
        padding: 10px;
        background-color: red;
    }

    .static.step.end {
        border-radius: 0.2em;
        margin-bottom: 5px;
        padding: 10px;
        background-color: red;
    }

    .card-text {
        margin-bottom: 0;
    }

    path.dragline {
        stroke: #000;
        stroke-width: 1px;
        cursor: default;
    }

    path.normalLine {
        stroke: #000;
        stroke-width: 0px;
        cursor: default;
    }

    .list-group {
        padding: 15px;
        display: inline-block;
        position: relative;
        max-height: 750px;
        overflow: scroll;

        -webkit-overflow-scrolling: touch;
    }

    .markers {
        font-size: 5px;
        fill: black;
        dominant-baseline: central
    }

    .markers #delete {
        font-size: 5px;
        fill: red;
    }

</style>