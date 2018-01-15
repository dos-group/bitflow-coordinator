<!--suppress VueDuplicateTag -->
<script type="text/x-template" id="axis-label-template">
  <text :x="point.x" :y="point.y">{{stat.label}}</text>
</script>
<template>
    <div class="page-content">
        <h1>Editor</h1>
        <div class="row" style="max-height: 750px">
            <div class="svg-container col-lg-2 col-md-2" style="border: 1px solid #42b983;padding-top:15px">
                <div v-on:click="createNode('start')" class="card step start" >
                    <div class="card-block">
                        <h5 class="card-title">Start of the pipeline </h5>
                        <p class="card-text">Source : </p>
                    </div>
                </div>
                <div v-on:click="createNode('end')" class="card step end">
                    <div class="card-block">
                        <h5 class="card-title">End of the pipeline</h5>
                        <p class="card-text">Results :</p>
                    </div>
                </div>
                    <div v-on:click="createNode(step.ID)" class="card step" v-for="step in allSteps">
                        <div class="card-block">
                            <h5 class="card-title">Step Id : {{step.ID}}</h5>
                            <p class="card-text">Number : {{step.Number}}</p>
                            <p class="card-text">Content : {{step.Content}}</p>
                            <p class="card-text">Typ : {{step.Typ}}</p>
                        </div>
                    </div>
            </div>
            <div class="svg-container col-lg-10 col-md-10">
                <svg preserveAspectRatio="xMidYMid meet" class="svg-content" viewBox="0 0 200 100"
                     style="border: 1px solid #42b983">
                    <g class="wholeGraph">
                        <g class="recs">
                            <g id="node" v-for="node in allNodes">
                                <rect width="20" height="15"  rx="1" ry="1" style="fill: rgb(31, 119, 180);"></rect>
                                <text font-family="FontAwesome" font-size="0.25em" dx="16"  v-on:click="deleteMe(node)" dy="4">X</text>
                                <text dx="1" dy="3" font-size="1.5px">
                                    <tspan>ID : {{node.ID}}</tspan>
                                </text>
                                <text dx="1" dy="6" font-size="1.5px">
                                    <tspan>Number : {{node.Number}}</tspan>
                                </text>
                                <text dx="1" dy="9" font-size="1.5px">
                                    <tspan>Typ : {{node.Typ}}</tspan>
                                </text>
                                <text dx="1" dy="12" font-size="1.5px">
                                    <tspan>Content : {{node.Content}}</tspan>
                                </text>
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
  import Vue from 'vue'

  export default {

    name: 'Editor',
    data() {
      var allNodes = [{
        "ID": 1,
        "Number": 1,
        "Typ": "source",
        "Content": "127.0.0.1:5555",
        "Params": [],
        "Successors": [2]
      }];

      var allSteps = [{
        "ID": 1,
        "Number": 1,
        "Typ": "source",
        "Content": "127.0.0.1:5555",
        "Params": [],
        "Successors": [2]
      },{"ID": 2,
          "Number": 1,
          "Typ": "source",
          "Content": "127.0.0.1:5555",
          "Params": [],
          "Successors": [2]
      },{"ID": 3,
        "Number": 1,
        "Typ": "source",
        "Content": "127.0.0.1:5555",
        "Params": [],
        "Successors": [2]
      },{"ID": 4,
        "Number": 1,
        "Typ": "source",
        "Content": "127.0.0.1:5555",
        "Params": [],
        "Successors": [2]
      }];

      return {allSteps,allNodes}
    },
    methods: {
      updateNodes : function () {
                var svg = d3.select("svg");

        svg.select(".recs").selectAll("g")
          .call(d3.drag()
            .on("start", dragstarted)
            .on("drag", dragged)
            .on("end", dragended));


        function dragstarted(d) {
          d3.select(this).raise().classed("active", true);
        }

        function dragged(d) {
          d3.select(this).attr('transform', 'translate(' + d3.event.x + ',' + d3.event.y + ')');
        }

        function dragended(d) {
          d3.select(this).classed("active", false);
        }
      },
      deleteMe: function(node){

        console.log(this.allNodes)

        this.allNodes.splice(this.allNodes.indexOf(node), 1)

        console.log(this.allNodes)
      },

      createNode: function (nodeId) {

        if (nodeId === "start"){

          var startNode = {
            "ID": "START",
            "Number": null,
            "Typ": null,
            "Content": null,
            "Params": [],
            "Successors": []
          };

          this.allNodes.push(startNode);

        }else if(nodeId === "end"){

          var endNode = {
            "ID": "END",
            "Number": null,
            "Typ": null,
            "Content": null,
            "Params": [],
            "Successors": null
          };

          this.allNodes.push(endNode);

        }else {
          this.allNodes.push(this.allSteps.find(findElement));
          }

        setTimeout(this.updateNodes, 100)

        function findElement(node) {
          return node.ID === nodeId;
        }




      }
    }
    ,
    mounted: function () {
//      Events.on('yourClickHandler', this.yourClickHandler);


      var Editor = function () {

        var editor = this;

        editor.start = function () {


          var svg = d3.select("svg");

/*
          var width = 20;
          var height = 15;
*/

/*
          var rectangles = d3.range(4).map(function () {
            return {
              data: {
                "ID": 10012,
                "Number": 1,
                "Typ": "source",
                "Content": "127.0.0.1:5555",
                "Params": [],
                "Successors": [
                  2
                ]
              },
              width: Math.round(width),
              height: Math.round(height)
            };
          });
*/


/*          var color = d3.scaleOrdinal()
            .range(d3.schemeCategory20);*/


          //svg.append("g").attr("class", "wholeGraph");

          //editor.nodes = svg.selectAll("g").append("g").attr("class", "recs").selectAll("g");
          //editor.lines = svg.selectAll("g").append("g").selectAll("g");

          svg.select(".recs").selectAll("g")
            .call(d3.drag()
            .on("start", dragstarted)
            .on("drag", dragged)
            .on("end", dragended));


/*          var number = 0;

          editor.nodes
            .data(rectangles)
            .enter()
            .append("g")
            .attr("id", function () {
              number += 1;
              return "node" + number
            })
            .call(d3.drag()
              .on("start", dragstarted)
              .on("drag", dragged)
              .on("end", dragended))
            .append("rect")
            .attr("width", function (d) {
              return d.width;
            })
            .attr("height", function (d) {
              return d.height;
            })
            .style("fill", function (d, i) {
              return color(i);
            });*/


/*          editor.recs = d3.select(".recs").selectAll("g");

          fillRecs(editor.recs, rectangles);

          editor.deleteNode = function (id) {
            svg.select("#" + id).remove();
          };*/


          var zoomed = function () {
            //this.state.justScaleTransGraph = true;
            d3.select(".wholeGraph")
              .attr('transform', 'translate(' + d3.event.transform.x + ',' + d3.event.transform.y + ') scale(' + d3.event.transform.k + ')');
          };

          var dragSvg = d3.zoom()
            .on("zoom", function () {
              if (d3.event.sourceEvent.shiftKey) {
                // TODO  the internal d3 state is still changing
                return false;
              } else {
                zoomed.call(svg);
              }
              return true;
            });

          svg.call(dragSvg).on("dblclick.zoom", null);

          function dragstarted(d) {
            d3.select(this).raise().classed("active", true);
          }

          function dragged(d) {
            d3.select(this).attr('transform', 'translate(' + d3.event.x + ',' + d3.event.y + ')');
          }

          function dragended(d) {
            d3.select(this).classed("active", false);
          }
        };


/*        var fillRecs = function (editorRecs, rectangles) {
          var pos = -1;

          editorRecs.append("text")
            .attr('font-family', 'FontAwesome')
            .attr('font-size', "0.25em")
            .attr("dx", "16")
            .attr("v-on:click", "yourClickHandler()")
            .attr("dy", "4")
            .text(function () {
              return '\uf118'
            })

          editorRecs.append("text")
            .attr("dx", "1")
            .attr("dy", "2")
            .attr("font-size", "1.5px")
            .append("tspan")
            .text(function () {
              pos = pos + 1;
              return "ID : " + rectangles[pos].data.ID;
            })

          pos = -1;

          editorRecs.append("text")
            .attr("dx", "1")
            .attr("dy", "5")
            .attr("font-size", "1.5px")
            .append("tspan")
            .text(function () {
              pos = pos + 1;
              return "Number : " + rectangles[pos].data.Number;
            })

          pos = -1;

          editorRecs.append("text")
            .attr("dx", "1")
            .attr("dy", "8")
            .attr("font-size", "1.5px")
            .append("tspan")
            .text(function () {
              pos = pos + 1;
              return "Typ : " + rectangles[pos].data.Typ;
            })


          pos = -1;

          editorRecs.append("text")
            .attr("dx", "1")
            .attr("dy", "11")
            .attr("font-size", "1.5px")
            .append("tspan")
            .text(function () {
              pos = pos + 1;
              return "Content : " + rectangles[pos].data.Content;
            })

        }*/
      };
      var graph = new Editor();
      graph.start();
    }
  }
</script>
<style>

    .active rect {
        stroke: #000;
        stroke-width: 0.3px;
    }

    .svg-container {
        display: inline-block;
        position: relative;
        width: 100%;
        padding-bottom: 50%;
        vertical-align: top;
        overflow: hidden;
    }

    .svg-content {
        display: inline-block;
        position: absolute;
        top: 0;
        left: 0;
    }

    .card.step{
        background-color: #3c763d;margin-bottom: 5px; padding: 10px;
    }

    .card.step.start{
        background-color: red;
    }

    .card.step.end{
        background-color: red;
    }

    .card-text {
        margin-bottom: 0 ;
    }

</style>