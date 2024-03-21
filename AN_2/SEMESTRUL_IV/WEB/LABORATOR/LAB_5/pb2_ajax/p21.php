<?php
    header("Access-Control-Allow-origin: *");

    $conn = mysqli_connect("localhost", "root", "", "lab5");

    $result = mysqli_query($conn, "select * from students");
    $rows = mysqli_num_rows($result);
    //print_r($rows);
    echo $rows;
