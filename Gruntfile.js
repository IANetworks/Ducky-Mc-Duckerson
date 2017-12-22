'use strict'

exports = module.exports = grunt => {
  require('time-grunt')(grunt)
  require('load-grunt-tasks')(grunt)

  //
  // load configuration
  grunt.initConfig({
    //
    // Report Generator
    todo: {
      options: {
        marks: [
          {
            pattern: 'TODO',
            color: 'orange'
          }, {
            pattern: 'FIXME',
            color: 'red'
          }, {
            pattern: 'NOTE',
            color: 'blue'
          }
        ],
        file: 'report.md',
        colophon: true,
        usePackage: true
      },
      src: [ 'src/**/*' ]
    }
  })

  //
  // register tasks
}
