`use strict`

const file = require('path')


exports.bot = require(file.join(__dirname, 'bot.js'))
exports.web = require(file.join(__dirname, 'web.js'))

exports.shutdown = () => {
    exports.bot.shutdown
    exports.web.stop
}