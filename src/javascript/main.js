var child = require('child_process').spawn('java', ['-jar', 'Ducky-Mc-Duckerson.jar'], {stdio: ['ipc', 'pipe', 'pipe']});

child.stdout.on('data', function(data) {
    console.log(data.toString());
});

child.stderr.on('data', function(data){
    console.log(data.toString());
});