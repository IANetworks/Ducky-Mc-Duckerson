<?php
    namespace DuckyUI\Core\Utils;

    use \Slim\Container;
    use \Slim\Http\Response;
    use \InvalidArgumentException;
    
    class RenderUtils
    {
        /** @var \Slim\Container $container The framework container */
        protected $container;
        
        /**
         * RenderUtils constructor.
         *
         * @param \Slim\Container The application controller.
         */
        public function __construct(Container &$aConstainer) {
            $this->container = $aConstainer;
        }
        
        /**
         * Get the text inside of a file.
         *
         * @param string $aPageTitle
         * @param int $aPageID
         * @param string $aContentTemplate
         * @param string $aNavFile
         * @param \Slim\Http\Response $aResponse
         * @param array $aValueArray
         *
         * @return \Slim\Http\Response
         *
         * @throws InvalidArgumentException
         * @throws RuntimeException
         */
        public function render($aPageTitle, $aPageId, $aContentTemplate, Response &$aResponse, &$aValueArray) {
            $aValueArray['PageTitle']   = $aPageTitle;
            $aValueArray['PageID']      = $aPageId;
            $aValueArray['PageNav']     = $this->container->renderer->fetch('nav_default.phtml', $aValueArray);
            $aValueArray['PageContent'] = $this->container->renderer->fetch($aContentTemplate, $aValueArray);
            $aValueArray['PageFooter']  = $this->container->renderer->fetch('footer.phtml', $aValueArray);

            return $this->container->renderer->render($aResponse, 'frame.phtml', $aValueArray);
        }
    }
    