<!--suppress VueDuplicateTag -->

<!--TODO: The first rec has to transform at the beginning so no line can be drawn
    TODO: Color themes for single steps (randomized or so)
    TODO: Size of text in recs might be too big (line breaks!)
-->
<template>
    <div class="page-content">
        <h1>Editor</h1>
        <div class="row" style="max-height: 750px">
            <div class="list-group col-lg-2 col-md-2">
                <div v-on:click="createNode('start')" class="list-group-item card step start">
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

                    <defs>
                        <marker id="Triangle" viewBox="0 0 10 10" refX="1" refY="5"
                                markerWidth="4" markerHeight="4" orient="auto">
                            <path d="M 0 0 L 10 5 L 0 10 z"></path>
                        </marker>
                    </defs>

                    <g class="wholeGraph">
                        <path hidden d="m0,0l0,0" class="dragline" style="marker-end: url(#Triangle)"></path>
                        <g class="lines">
                            <path v-for="line in allLines" d="m0,0l0,0" class="dragline"
                                  style="marker-end: url(#Triangle)"></path>
                        </g>
                        <g class="recs">
                            <g id="node" transform="translate(10,10)" v-for="node in allNodes">
                                <rect width="20" height="15" rx="1" ry="1" style="fill: rgb(31, 119, 180);"></rect>
                                <text font-family="FontAwesome" font-size="0.25em" dx="16" v-on:click="deleteMe(node)"
                                      dy="4">X
                                </text>
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
  import Vue from 'vue'

  export default {

    name: 'Editor',
    data() {

      /*    TODO : let the blobs disappear when not needed
            v-on:mouseout="blobs = !blobs" v-on:mouseover="blobs = !blobs"*/
      var blobs = true;

      var allNodes = [];

      var allSteps = [{
        "ID": 1,
        "Number": 1,
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
        "Number": 1,
        "Typ": "source",
        "Content": "127.0.0.1:5555",
        "Params": [],
        "Successors": []
      }, {
        "ID": 4,
        "Number": 1,
        "Typ": "source",
        "Content": "127.0.0.1:5555",
        "Params": [],
        "Successors": []
      }, {
        "ID": 3,
        "Number": 1,
        "Typ": "source",
        "Content": "127.0.0.1:5555",
        "Params": [],
        "Successors": []
      }
        , {
          "ID": 3,
          "Number": 1,
          "Typ": "source",
          "Content": "127.0.0.1:5555",
          "Params": [],
          "Successors": []
        }
        , {
          "ID": 3,
          "Number": 1,
          "Typ": "source",
          "Content": "127.0.0.1:5555",
          "Params": [],
          "Successors": []
        }
        , {
          "ID": 3,
          "Number": 1,
          "Typ": "source",
          "Content": "127.0.0.1:5555",
          "Params": [],
          "Successors": []
        }
      ];

      var allLines = [];

      return {allSteps, allNodes, blobs, allLines}
    },
    methods: {
      updateNodes: function () {
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

      updateLines: function () {
        var svg = d3.select("svg");

        svg.select(".recs").selectAll("circle")
          .call(d3.drag()
            .on("start", dragstarted)
            .on("drag", dragged)
            .on("end", dragended));


        function dragstarted(d) {
          d3.select(".dragline").attr('hidden', null);
          var str = d3.select(this.parentNode).attr('transform')
          var res = str.split("(")[1].split(",");
          var start = parseInt(res[0]) + 10;
          var end = parseInt(res[1].split(")")[0]) + 7.5
          d3.select(".dragline").attr('d', 'm' + start + "," + end + 'l' + d3.event.x + ',' + d3.event.y);
          d3.select(this).raise().classed("active", true);
        }

        function dragged(d) {
          var str = d3.select(this.parentNode).attr('transform')
          var res = str.split("(")[1].split(",");
          var start = parseInt(res[0]) + 10;
          var end = parseInt(res[1].split(")")[0]) + 7.5
          d3.select(".dragline").attr('d', 'm' + start + "," + end + 'l' + d3.event.x + ',' + d3.event.y);
        }

        function dragended(d) {
          d3.select(".dragline").attr('hidden', 'hidden');
          d3.select(this).classed("active", false);
        }
      },

      deleteMe: function (node) {
        this.allNodes.splice(this.allNodes.indexOf(node), 1)
      },

      createNode: function (nodeId) {

        if (nodeId === "start") {

          var startNode = {
            "ID": "START",
            "Number": null,
            "Typ": null,
            "Content": null,
            "Params": [],
            "Successors": []
          };

          this.allNodes.push(startNode);

        } else if (nodeId === "end") {

          var endNode = {
            "ID": "END",
            "Number": null,
            "Typ": null,
            "Content": null,
            "Params": [],
            "Successors": null
          };

          this.allNodes.push(endNode);

        } else {
          this.allNodes.push(this.allSteps.find(findElement));
        }

        setTimeout(this.updateNodes, 100)
        setTimeout(this.updateLines, 100)

        function findElement(node) {
          return node.ID === nodeId;
        }

      }
    }
    ,
    mounted: function () {

      var svg = d3.select("svg");

      var zoomed = function () {
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

      this.updateNodes;
      this.updateLines;

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

    .card.step {
        background-color: #3c763d;
        margin-bottom: 5px;
        padding: 10px;
    }

    .card.step.start {
        background-color: red;
    }

    .card.step.end {
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

    .list-group {
        padding: 15px;
        display: inline-block;
        position: relative;
        max-height: 750px;
        overflow: scroll;

        -webkit-overflow-scrolling: touch;
    }

</style>