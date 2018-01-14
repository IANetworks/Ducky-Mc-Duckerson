'use strict'

// this just quiets the linter until I can get production-ready systems going
var $ = window.$

// Mann's script, de-linted
$(function () {
  var socket = window.io()

  $('form').submit(function () {
    socket.emit('consoleMessage', $('#m').val())
    $('#m').val('')
    return false
  })

  socket.on('consoleLog', function (msg) {
    JSON.stringify(msg, null, 4)
    $('#messages').append($('<li>').text(msg))
  })
})
