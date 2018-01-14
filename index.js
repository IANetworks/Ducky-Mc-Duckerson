'use strict'

import Ducky from './app'

console.log('starting up, just a moment...')
global.Ducky = new Ducky(process.argv.slice(2))
