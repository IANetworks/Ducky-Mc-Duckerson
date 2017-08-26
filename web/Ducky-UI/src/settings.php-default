<?php
    use Monolog\Logger;

    $jsDir  = __DIR__ . '/../public/js';
    $cssDir = __DIR__ . '/../public/css';

    return [
        'settings' => [
            'displayErrorDetails' => True, // set to False in production
            'addContentLengthHeader' => False, // Allow the web server to send the content-length header
            'appRootDir' => __DIR__ . '/../',

            // Renderer settings
            'renderer' => [
                'template_path' => __DIR__ . '/../templates/',
            ],

            // Monolog settings
            'logger' => [
                'name' => 'Ducky-UI',
                'path' => __DIR__ . '/../logs/app-' . date('Y-m-d', time()) . '.log', // To create one log file per day. Change 'Y' to 'o' for ISO-8601 compliance
                'level' => Logger::DEBUG,
            ],

            /*
             * Engine possibilities:
             *   cubrid       > Cubrid !!(Needs recompilation of PHP)
             *   dblib        > FreeTDS / Microsoft SQL Server / Sybase !!(DEPRECATED, DNU)
             *   firebird     > Firebird/Interbase !!(Requires Interbase/Firebird tools)
             *   ibm          > IBM DB2 !!(Needs recompilation of PHP)
             *   informix     > IBM Informix Dynamic Server !!(Needs recompilation of PHP)
             *   mysql        > MySQL 3.x/4.x/5.x
             *   oci          > Oracle Call Interface !!(Requires Oracle tools)
             *   odbc         > ODBC v3 (IBM DB2, unixODBC and win32 ODBC) !!(DNU)
             *   pgsql        > PostgreSQL
             *   sqlite       > SQLite 2/3
             *   sqlsrv       > Microsoft SQL Server / SQL Azure !!(Requires an additional download:
             *                  https://github.com/Microsoft/msphpsql/releases)
             *   4d           > 4D !!(Needs recompilation of PHP)
             *   influxdb     > Influx timeseries Database WebAPI
             *   udp+influxdb > Influx timeseries Database UDP Socket
             *
             * If you choose the sqlite engine, you should use 'host' as the file path
             */
            'database' => [
                'engine' => 'sqlite',
                'host' => 'localhost',
                'user' => 'aUser',
                'password' => 'aPassword',
                'database' => 'default'
            ],

            // Minifier settings
            'minifier' => [
                'css' => [
                    $cssDir . '/main.css'               => $cssDir . '/main.min.css'
                ],
                'js' => [
                    $jsDir . '/main.js'                   => $jsDir . '/main.min.js'
                ],
                'hashFile' => __DIR__ . '/../tmp/minifyHashes.json'
            ]
        ]
    ];
