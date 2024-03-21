<?php

    header("Access-Control-Allow-origin: *");

    $conn = mysqli_connect("localhost", "root", "", "trenuriweb");

    if($conn->connect_error){
        exit('Could not connect to database!');
    }

    //echo $_GET['depart'];

    $sql = "SELECT sosire FROM trenuri WHERE plecare = ?;";
    $stmt = $conn->prepare($sql);
    $stmt -> bind_param('s', $_GET['depart']);
    $stmt -> execute();
    $result = $stmt->get_result();

    echo "<table border=\"1px solid black\">";

    while($row = $result->fetch_assoc()){
        echo "<tr>";
        echo "<td>".$row['sosire']."</td>";
        echo "</tr>";
    }

    echo "</table>";
    $conn -> close();