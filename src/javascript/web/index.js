const express = require('express')
const app = express()

app.get('/', (req, res) => {
    res.send('Hello squirrels')
    process.send({event:'WebHit', data:{location:'Squirrel', file:'root'}})
})

app.get('/goi', (req, res) => {
    res.send('Hello Gearheads, ready to fly by the seat of your pants? Also, bunny!')
    process.send({event:'WebHit', data:{location:'Gearheads', file:'goi'}})
})


app.listen(80, () => console.log('App listening at port 80! Cause I BE BUNNY!'))

//process.on('message', (thisMessage) => messageRecieved)

