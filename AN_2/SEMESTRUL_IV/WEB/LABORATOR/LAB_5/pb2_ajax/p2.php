<?php
    header("Access-Control-Allow-origin: *");

    $conn = mysqli_connect("localhost", "root", "", "lab5");

    $limit = 3;
    $page = $_GET['page'];
    $page_index = ($page-1) * $limit; 
    $All_Users=mysqli_query($conn,"select * from students limit $page_index, $limit");

    $result = mysqli_query($conn, "select * from students");
    $rows = mysqli_num_rows($result);

    echo "<table border=\"1px solid black\">";
    echo "<tr>";
    echo "<th>Nume </th> <th>Prenume </th> <th>Telefon</th> <th>Email</th>";
    echo "</tr>";
    while($row=mysqli_fetch_array($All_Users))
    {
        echo "<tr>";
        echo "<td>".$row['Nume']."</td>";
        echo "<td>".$row['Prenume']."</td>";
        echo "<td>".$row['Telefon']."</td>";
        echo "<td>".$row['Email']."</td>";
        echo "</tr>";
    }

    echo "</table> <br>";
    $conn -> close();
