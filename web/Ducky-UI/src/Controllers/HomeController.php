<?php
    namespace DuckyUI\Controllers;

    use \Slim\Http\Request;
    use \Slim\Http\Response;
    use \DuckyUI\AbstractClasses\PageController;
    use \InvalidArgumentException;

    class HomeController extends PageController
    {
        /**
         * HomeController invoker.
         *
         * @param \Slim\Http\Request $request
         * @param \Slim\Http\Response $response
         * @param array $args
         *
         * @return \Slim\Http\Response
         */
        public function __invoke(Request $request, Response $response, $args) {
            return $response;
        }

        /**
         * Show the default page.
         *
         * @param \Slim\Http\Request $request
         * @param \Slim\Http\Response $response
         * @param array $args
         *
         * @return \Slim\Http\Response
         */
        public function home(Request $request, Response $response, $args) {
            $this->container->logger->info("DuckyUI '/" . (Empty($args['catchall']) ? "" : $args['catchall']) . "' route");

            $pageTitle            = 'Home Page';
            $pageId               = 0;
            $contentTemplate      = 'index.phtml';
            $values['PageCrumbs'] = "<ol class=\"breadcrumb\">" . PHP_EOL .
                                    "    <li class=\"active\">Home</li>" . PHP_EOL .
                                    "</ol>";

            return $this->container->renderUtils->render($pageTitle, $pageId, $contentTemplate, $response, $values);
        }
    }
