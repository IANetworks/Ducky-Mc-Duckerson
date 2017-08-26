<?php
    // Routes
    use \DuckyUI\Controllers;

    // API group routes
    $app->group('/api', function () {
        $this->group('/v1', function () {
        });
    });

/**
 * Examples:
 *    $app->post('/register', Controllers\AAAController::class . ':register')->setName('register');
 *    $app->post('/login', Controllers\AAAController::class . ':login')->setName('login');
 *    $app->get('/logout', Controllers\AAAController::class . ':logout')->setName('logout');
 *
 *    // About page route
 *    $app->group('/about[/{catchall}]', Controllers\HomeController::class . ':about')->setName('about');
 */

    // Default route to /home
    $app->get('/[{catchall}]', Controllers\HomeController::class . ':home')->setName('home');
