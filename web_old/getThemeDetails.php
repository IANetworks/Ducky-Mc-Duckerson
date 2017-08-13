<?php
include('header.php');

$cf = new Config('../config.properties'); //I know, could have used ini, in hindsight that would have been smart but Abby went with Java .properties and now Abby pays for it
$databaseName = $cf->getConfig('database_name');

if(!is_null($databaseName))
{
    $db = new SQLite3($databaseName . '.db', SQLITE3_OPEN_READONLY);
    $resultset = $db->query('SELECT * FROM theme_detail');
    $full_data = array();
    while($row = $resultset->fetchArray())
    {
        $full_data[$row['id_key']] = $row;
    }
    header("Content-type: application/json");
    echo json_encode($full_data, JSON_PRETTY_PRINT);
}

    



?>