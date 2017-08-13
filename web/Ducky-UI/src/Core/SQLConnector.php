<?php
    namespace DuckyUI\Core;

    use \Slim\Container;
    use \Slim\PDO\Database;
    use \Slim\PDO\Statement;
    use \PDO;
    use \PDOException;
    use \InvalidArgumentException;

    class SQLConnector
    {
        /** @var \Slim\Container $container The framework container */
        protected $container;
        /** @var string $DSN The Data Source Name */
        protected $DSN;
        /** @var mixed $PDO The Php Data Object */
        public $PDO;

        /**
         * Class constructor.
         *
         * @param \Slim\Container The application controller.
         */
        public function __construct(Container &$aConstainer) {
            $this->container = $aConstainer;
        }

        /**
         * Build a new DSN.
         *
         * @param array The database settings.
         */
        protected function buildDSN($aConfig) {
            if ($aConfig['engine'] == "sqlite")
                $this->DSN = $aConfig['engine'] .
                             ":" . $aConfig['host'];
            elseif (in_array($aConfig['engine'], ["dblib", "sqlsrv"]))
                $this->DSN = $aConfig['engine'] .
                             ":Server=" . $aConfig['host'] .
                             ";Database=" . $aConfig['database'];
            else
                $this->DSN = $aConfig['engine'] .
                             ":host=" . $aConfig['host'] .
                             ";dbname=" . $aConfig['database'] .
                             ";charset=utf8";
        }

        /**
         * connect Connect to the specifieddatabase.
         *
         * @return bool Indicator wether the connection succeeded or not.
         */
        public function connect() {
            $config = $this->container->get('settings')['database'];
            $this::buildDSN($config);

            try {
                $this->PDO = new Database($this->DSN, $config['user'], $config['password'], array(PDO::ATTR_ERRMODE => PDO::ERRMODE_WARNING));
            } catch (Exception $ex) {
                $this->container->logger->error("Unable to connect to the target database: " . print_r($ex, True));

                return False;
            };

            $this->PDO->setAttribute(PDO::ATTR_ERRMODE,            PDO::ERRMODE_EXCEPTION);
            $this->PDO->setAttribute(PDO::ATTR_DEFAULT_FETCH_MODE, PDO::FETCH_ASSOC);
            $this->PDO->setAttribute(PDO::ATTR_EMULATE_PREPARES,   False);

            return True;
        }

        /**
         * Close the database connection.
         */
        public function close() {
            $this->PDO = null;
        }
    }
