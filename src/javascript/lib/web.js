'use strict'

const childProcess = require('child_process')
const path = require('path')

exports.start = () => {
  const webDir = path.join(__dirname, '..', 'web')

  const pr = childProcess.fork(webDir, [ ], { cwd: webDir, stdio: ['pipe', 1, 2, 'ipc']})
  exports._process = pr

  pr.on('error', err => {
    Util.log.error('failed to start web daemon')
    console.log(err.stack)
  })
}

exports.stop = () => {
  if (exports._process) exports._process.send('stop')
}