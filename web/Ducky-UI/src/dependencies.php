<?php
    use \Slim\Views\PhpRenderer;
    use \Monolog\Logger;
    use \Monolog\Processor\UidProcessor;
    use \Monolog\Handler\StreamHandler;
    use \DuckyUI\Core;
    use \DuckyUI\Core\Utils;

    // DIC configuration
    $container = $app->getContainer();

    // view renderer
    $container['renderer'] = function ($c) {
        $settings = $c->get('settings')['renderer'];

        return new PhpRenderer($settings['template_path']);
    };

    // monolog
    $container['logger'] = function ($c) {
        $settings = $c->get('settings')['logger'];
        $logger   = new Logger($settings['name']);

        $logger->pushProcessor(new UidProcessor());
        $logger->pushHandler(new StreamHandler($settings['path'], $settings['level']));

        return $logger;
    };

    // File Utilities
    $container['fileUtils'] = function ($c) {
        return new Utils\FileUtils($c);
    };

    // HTML rendering utilities
    $container['renderUtils'] = function ($c) {
        return new Utils\RenderUtils($c);
    };

    // Database connection handler
    $container['dataBase'] = function ($c) {
        $dbClient = new Core\SQLConnector($c);

        if (!$dbClient->connect())
            throw new Exception('Unable to connect to the database!');

        return $dbClient;
    };
