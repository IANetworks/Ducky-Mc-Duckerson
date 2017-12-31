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
    },

    //
    // Shell scripts
    shell: {
      mavenPackage: 'mvn package',
      mavenTest: 'mvn test',
      mavenClean: 'mvn clean'
    },

    //
    // Copy files
    copy: {
      maven: {
        src: 'target/Ducky*.jar',
        dest: 'src/main/javascript/bot/Ducky-Mc-Duckerson.jar'
      }
    },

    //
    // Cleanup
    clean: {
      jars: {
        src: 'src/**/*.jar'
      }
    }
  })

  //
  // register tasks
  grunt.registerTask('build', [
    'shell:mavenPackage',
    'copy:maven'
  ])
  grunt.registerTask('test', [
    'shell:mavenTest'
  ])
  grunt.registerTask('clean', [
    'shell:mavenClean',
    'clean:jars'
  ])
}
