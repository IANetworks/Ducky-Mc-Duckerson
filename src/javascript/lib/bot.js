`use strict`

const childProcess = require('child_process')
const file = require('path')

exports.start = () => {
    const botDir = file.join(__dirname, '..', 'bot', 'Ducky-Mc-Duckerson.jar')
   
    const botProcess = childProcess.spawn('java', ['-jar', botDir], {stdio: ['ipc', 'pipe', 'pipe']});
    exports.__botProcess = botProcess

    botProcess.stdout.on('data', function(data) {
        process.send(data);
    })
   
   botProcess.stderr.on('data', function(data){
        process.send(data);
   })

   botProcess.on('error', err => {
       process.send(err)
   })

   

}

exports.stop = () => {
    if(exports.__botProcess) exports.__botProcess.send('stop')
}

