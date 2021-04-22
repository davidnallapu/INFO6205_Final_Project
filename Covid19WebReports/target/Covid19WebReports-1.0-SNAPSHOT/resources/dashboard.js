/* globals Chart:false, feather:false */


const queryString = window.location.search;
const urlParams = new URLSearchParams(queryString);
var scatterPlot;
$.ajaxSetup({headers: {"cache-control": "no-cache"}});

function createChart(labels, data, ctx, datalabel, color) {
    'use strict'

    feather.replace();

    var myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                    label: datalabel,
                    data: data,
                    lineTension: 0,
                    backgroundColor: 'transparent',
                    borderColor: color,
                    borderWidth: 2,
                    pointBackgroundColor: color
                }]
        }
    });

}

function createScatterChart(locations, ctx, datalabel, color) {
    'use strict'

    feather.replace();

    const data = {
        datasets: [{
                label: datalabel,
                data: locations,
                backgroundColor: 'rgb(255, 99, 132)'
            }],
    };

    if (!scatterPlot) {

        scatterPlot = new Chart(ctx,
                {
                    type: 'scatter',
                    data: data,
                    options: {
                        scales: {
                            x: {
                                type: 'linear',
                                position: 'bottom'
                            }
                        }
                    }
                });
    } else {
        console.log(locations);
        scatterPlot.data.datasets[0].data = locations;
        scatterPlot.update();
    }
}

function displayTable(labels, data, diff) {

    var table = document.getElementById('rawdata');

    for (var i = 0; i < data.length; i++) {

        var row = table.insertRow();
        var cell1 = row.insertCell(0);
        var cell2 = row.insertCell(1);
        var cell3 = row.insertCell(2);
        var cell4 = row.insertCell(3);

        cell1.innerHTML = i + 1;
        cell2.innerHTML = labels[i];
        cell3.innerHTML = data[i];
        cell4.innerHTML = diff[i];
    }

}


$(document).ready(function () {
    
    
    
    var url = '';
    if (!urlParams.get('model')) url = 'covid_1.json';
    else url = urlParams.get('model')
    
    $.getJSON('resources/' + url, function (results) {

        //console.log('resources/' + urlParams.get('model'));

        var labels = [];
        var data = [];
        var diff = [];

        var labels = results.data.map(function (item) {
            return item.time;
        });

        var data = results.data.map(function (item) {
            return item.value;
        });

        for (var i = 1; i < data.length; i++) {
            diff[i] = data[i] - data[i - 1];
        }

        var summary = document.getElementById('summarytable');

        for (item in results.summary) {

            var row = summary.insertRow();
            var cell1 = row.insertCell(0);
            var cell2 = row.insertCell(1);

            cell1.innerHTML = item;
            cell2.innerHTML = results.summary[item];

        }


        // Create chart
        createChart(labels, data, document.getElementById('myChart'), 'Number of Infected people', '#007bff');
        createChart(labels, diff, document.getElementById('myChart2'), 'Speed of the Spread', '#ff0000');

        displayTable(labels, data, diff);

    });
});

//
//var myVar = setInterval(updatePeopleChart, 1000);
//
//function updatePeopleChart() {
//    $.ajaxSetup({ cache: false });
//    $.ajax({
//        cache: false,
//        url: "data.json?step=5",
//        dataType: "json",
//        success: function (results) {
//
//            var locations = results.people.persons.map(function (p) {
//                return p.location;
//            });
//
//            console.log(locations);
//            // Create chart
//            createScatterChart(locations, document.getElementById('myScatterPlot'), 'Population', '#007bff');
//        }});
//
//
//}
