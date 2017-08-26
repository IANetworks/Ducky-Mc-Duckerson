<?php
    namespace DuckyUI\AbstractClasses;

    use \Slim\Http\Request;
    use \Slim\Http\Response;
    use \Slim\Container;

    abstract class PageController
    {
        /** @var \Slim\Container $container The framework container */
        protected $container;

        /**
         * Class constructor.
         *
         * @param \Slim\Container $container
         */
        public function __construct(Container $container) {
            $this->container = $container;
        }

        /**
         * Class invoker.
         *
         * @param \Slim\Http\Request $request
         * @param \Slim\Http\Response $response
         * @param array $args
         *
         * @return \Slim\Http\Response
         */
        public abstract function __invoke(Request $request, Response $response, $args);

        /**
         * Show the default page.
         *
         * @param \Slim\Http\Request $request
         * @param \Slim\Http\Response $response
         * @param array $args
         *
         * @return \Slim\Http\Response
         */
        public abstract function home(Request $request, Response $response, $args);
    }
