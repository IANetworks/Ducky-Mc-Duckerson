const express = require('express')
const app = express()
var http = require('http').Server(app)
var io = require('socket.io')(http)
const fs = require('fs')

let port = 3000
try {
    fs.statSync('../config.json')
    port = require('../config.json').socket.port
} catch (e) {
    if (!e.message.startsWith('ENOENT')) throw e
}

app.get('/', (req, res) => {
    res.send('Hello squirrels')
    process.send({event:'WebHit', data:{location:'Squirrel', file:'root'}})
})

app.get('/console', (req, res) => {
    res.sendFile(__dirname + '/console.html')
})

app.get('/goi', (req, res) => {
    res.send('Hello Gearheads, ready to fly by the seat of your pants? Also, bunny!')
})

io.on('connection', function(socket){
    console.log(`a user connected`)
    socket.on('disconnect', function(){
        console.log('user disconnected')
    })
    socket.on('consoleMessage', function(msg){
        console.log('consoleMessage: ' + msg)
        io.emit('consoleMessage', msg)
    });
    socket.on('consoleLog', function(msg) {
        //console.log('consoleLog:' + msg)
        io.emit('consoleLog', msg)
    })
})

http.listen(port, () => console.log('App listening at port ' + port + '! Cause I BE BUNNY!'))

//process.on('message', (thisMessage) => messageRecieved)

