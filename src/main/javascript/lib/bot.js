`use strict`

const childProcess = require('child_process')
const file = require('path')

exports.start = () => {
    const botDir = file.join(__dirname, '..', 'bot', 'Ducky-Mc-Duckerson.jar')
   
    const botProcess = childProcess.spawn('java', ['-jar', botDir], {stdio: ['ipc', 'pipe', 'pipe']});
    exports.__botProcess = botProcess

    botProcess.stdout.on('data', function(data) {
        console.log(data.toString());
    })
   
   botProcess.stderr.on('data', function(data){
        console.log(data.toString());
   })

   botProcess.on('error', err => {
       console.log(err.toString())
   })

   

}

exports.stop = () => {
    if(exports.__botProcess) exports.__botProcess.send('stop')
}

