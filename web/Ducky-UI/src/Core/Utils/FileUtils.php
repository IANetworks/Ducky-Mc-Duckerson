<?php
    namespace DuckyUI\Core\Utils;

    use \Slim\Container;
    use \MatthiasMullie\Minify;
    use \Imagick;
    use \InvalidArgumentException;

    class FileUtils
    {
        /** @var \Slim\Container $container The framework container */
        private $container;

        /**
         * FileUtils constructor.
         *
         * @param \Slim\Container The application controller.
         */
        public function __construct(Container &$aContainer) {
            $this->container = $aContainer;
        }

        /**
         * Get the text inside of a file.
         *
         * @param string $aFile
         *
         * @return string
         */
        public function getFileText($aFile) {
            $fileHandle = fopen($aFile, 'r');
            $result     = '';

            if ($fileHandle !== False) {
                while ($line = fgets($fileHandle)) {
                    $result .= $line;
                };

                fclose($fileHandle);
            };

            return $result;
        }

        /**
         * Perform the main actions on the files and determine if it should/can be minified.
         *
         * @param array $arr
         * @param string $type
         */
        private function minify($arr, $type) {
            $settings = $this->container->get('settings')['minifier'];
            $hashArr  = file_exists($settings['hashFile']) ? json_decode(file_get_contents($settings['hashFile']), True) : array();

            foreach ($arr as $inFile => $outFile) {
                // Skip the file if the source does not exist
                if (!file_exists($inFile)) {
                    $this->container->logger->err("Error: File '" . $inFile . "' does not exist, Skipping.");
                    continue;
                };

                // Skip the file if the source hashes are equal
                if (array_key_exists($inFile, $hashArr) && $hashArr[$inFile] == sha1(file_get_contents($inFile)))
                    continue;

                $writeHandle = fopen($outFile, 'w');

                // Skip the file if the destination can not be opened for writing
                if (!$writeHandle) {
                    $this->container->logger->err("Error: Unable to open file '" . $outFile . "', Skipping.");
                    continue;
                };

                if ($type == 'CSS')
                    $minifier = new Minify\CSS($inFile);
                elseif ($type == 'JS')
                    $minifier = new Minify\JS($inFile);
                else // Completely ignore bad types
                    continue;

                fwrite($writeHandle, $minifier->minify());
                fclose($writeHandle);

                $hashArr[$inFile] = sha1(file_get_contents($inFile));
                $this->container->logger->info("Successfully minified file '" . $inFile . "' to '" . $outFile . "'.");
            };

            file_put_contents($settings['hashFile'], json_encode($hashArr, JSON_PRETTY_PRINT));
        }

        /**
         * Minify all JS files in the array.
         *
         * @param array $arr
         */
        public function minifyJS($arr){
            $this->minify($arr, 'JS');
        }

        /**
         * Minify all CSS files in the array.
         *
         * @param array $arr
         */
        public function minifyCSS($arr){
            $this->minify($arr, 'CSS');
        }
    }
