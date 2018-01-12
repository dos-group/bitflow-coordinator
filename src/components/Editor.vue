<template>
    <div class="page-content">
        <h1>Editor</h1>
        <div class="row">

            <div class="svg-container col-lg-2 col-md-2">
            </div>
            <div class="svg-container col-lg-10 col-md-10">
                <svg preserveAspectRatio="xMidYMid meet" class="svg-content" viewBox="0 0 150 100"
                     style="border: #3c763d 1px solid"></svg>
            </div>
        </div>

    </div>
</template>
<script>
    import * as d3 from "d3"

    export default {

        name: 'Editor',
        data() {
            return {}
        },
        mounted: function () {


            var Editor = function () {

                var editor = this;

                editor.start = function () {


                    var svg = d3.select("svg");

                    var width = 20;
                    var height = 15;

                    var rectangles = d3.range(4).map(function () {
                        return {
                            width: Math.round(width),
                            height: Math.round(height)
                        };
                    });

                    var color = d3.scaleOrdinal()
                        .range(d3.schemeCategory20);

                    var position = 0;

                    svg.append("g").attr("class", "editorNodes");

                    editor.nodes = svg.selectAll("g").append("g").selectAll("g");
                    editor.lines = svg.selectAll("g").append("g").selectAll("g");

                    editor.nodes
                        .data(rectangles)
                        .enter().append("rect")
                        .attr("width", function (d) {
                            return d.width;
                        })
                        .attr("height", function (d) {
                            return d.height;
                        })
                        .attr("y", function (d) {
                            position = position + 1;
                            return d.height * position;
                        })
                        .style("fill", function (d, i) {
                            return color(i);
                        })
                        .call(d3.drag()
                            .on("start", dragstarted)
                            .on("drag", dragged)
                            .on("end", dragended));

                    var zoomed = function () {
                        console.log(d3.event.transform.x)
                        //this.state.justScaleTransGraph = true;
                        d3.select(".editorNodes")
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
                        d3.select(this).attr("x", d.x = d3.event.x).attr("y", d.y = d3.event.y);
                    }

                    function dragended(d) {
                        d3.select(this).classed("active", false);
                    }
                }
            };
            var graph = new Editor();
            graph.start();
        }
    }

</script>
<style>

    .active {
        stroke: #000;
        stroke-width: 1px;
    }

    .svg-container {
        display: inline-block;
        position: relative;
        width: 100%;
        padding-bottom: 100%;
        vertical-align: top;
        overflow: hidden;
    }

    .svg-content {
        display: inline-block;
        position: absolute;
        top: 0;
        left: 0;
    }

</style>