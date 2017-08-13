<?php
class Config
    {
        //for parsing properties and storing configuration.
        private $configArray;
        private $isSet = false;
        
        function __construct($configText)
        {
            $this->parseProperties($configText);
        }
        
        function parseProperties($configText)
        {
            if(isset($configText))
            {
                $fileContent = file_get_contents($configText);
                
                $result = array();
                $lines = split("\n", $fileContent);
                $key = "";
                $isWaitingOtherLine = false;
            
                foreach($lines as $i=>$line) {
                    if(empty($line) || (!$isWaitingOtherLine && strpos($line,"#") === 0)) continue;
            
                    if(!$isWaitingOtherLine) {
                        $key = substr($line,0,strpos($line,'='));
                        $value = substr($line,strpos($line,'=') + 1, strlen($line));
                    } else {
                        $value .= $line;
                    }
            
                    /* Check if ends with single '\' */
                    if(strrpos($value,"\\") === strlen($value)-strlen("\\")) {
                        $value = substr($value, 0, strlen($value)-1)."\n";
                        $isWaitingOtherLine = true;
                    } else {
                        $isWaitingOtherLine = false;
                    }
            
                    $result[$key] = $value;
                    unset($lines[$i]);
                }
            
                $this->configArray = $result;
                $this->isSet = true;
            } else { 
                $this->isSet = false;
            }
        }
        
        function getConfig($configName)
        {
            if($this->isSet and array_key_exists($configName, $this->configArray))
            {
                return $this->configArray[$configName];
            } else {
                return null;
            }
        }
        
    }
?>