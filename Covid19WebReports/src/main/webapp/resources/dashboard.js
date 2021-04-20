/* globals Chart:false, feather:false */


function createChart(labels, data, ctx) {
    'use strict'

    feather.replace();

    var myChart = new Chart(ctx, {
        type: 'line',
        data: {
            labels: labels,
            datasets: [{
                label: 'Sample Data',
                data: data,
                lineTension: 0,
                    backgroundColor: 'transparent',
                    borderColor: '#007bff',
                    borderWidth: 4,
                    pointBackgroundColor: '#007bff'
            }]
        },
    });

}

$.getJSON('resources/sample.json').done( function (results) {  

        var labels = [];
        var data = [];

        var labels = results.data.map(function (item) {
            return item.time;
        });

        var data = results.data.map(function (item) {
            return item.number;
        });

        // Create chart
        createChart(labels, data, document.getElementById('myChart'));

});

$.getJSON('resources/sample_1.json').done( function (results) {  

        var labels = [];
        var data = [];

        var labels = results.data.map(function (item) {
            return item.time;
        });

        var data = results.data.map(function (item) {
            return item.number;
        });

        // Create chart
        createChart(labels, data, document.getElementById('myChart1'));

});
