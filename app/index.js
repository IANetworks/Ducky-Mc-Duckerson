'use strict'

import { EventEmitter } from 'event'

export default class Ducky extends EventEmitter {
  constructor (args) {
    super()

    Object.defineProperty(this, 'args', { value: args, enumerable: true })
  }
}
