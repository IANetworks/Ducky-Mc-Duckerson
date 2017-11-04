'use strict'

console.log('Host staring up.');

const file = require('path');

global.Util = require(file.join(__dirname, 'lib', 'util.js'));
Util.web.start();
Util.bot.start();