<?php
    // Application middleware
    use \Psr7Middlewares\Middleware\TrailingSlash;

    $app->add((new TrailingSlash(False))->redirect(301));
