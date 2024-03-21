<?php
    header("Access-Control-Allow-Origin: *");

    //divizare in caractere individuale
    $array = str_split($_GET['str']);

    //vedem daca e gata jocul
    $isfinished = 1;
    for($i = 0; $i < 9; $i ++)
    if($array[$i] == "-")
        $isfinished = 0;

    //daca nu-i gata, alegem o mutare
    if($isfinished == 0){
        $index = rand(0, 8);
        while($array[$index] != "-")
            $index = rand(0, 8);

        $array[$index] = 'O';
    }

    $x = 0;
    $o = 0;
    if($array[0] == 'X' and $array[1] == 'X' and $array[2] == 'X')
        $x = 1;

    if($array[3] == 'X' and $array[4] == 'X' and $array[5] == 'X')
        $x = 1;

    if($array[6] == 'X' and $array[7] == 'X' and $array[8] == 'X')
        $x = 1;

    if($array[0] == 'X' and $array[4] == 'X' and $array[8] == 'X')
        $x = 1;

    if($array[2] == 'X' and $array[4] == 'X' and $array[6] == 'X')
        $x = 1;

    if($array[0] == 'X' and $array[3] == 'X' and $array[6] == 'X')
        $x = 1;

    if($array[1] == 'X' and $array[4] == 'X' and $array[7] == 'X')
        $x = 1;

    if($array[2] == 'X' and $array[5] == 'X' and $array[8] == 'X')
        $x = 1;

    if($array[0] == 'O' and $array[1] == 'O' and $array[2] == 'O')
        $o = 1;

    if($array[3] == 'O' and $array[4] == 'O' and $array[5] == 'O')
        $o = 1;

    if($array[6] == 'O' and $array[7] == 'O' and $array[8] == 'O')
        $o = 1;

    if($array[0] == 'O' and $array[4] == 'O' and $array[8] == 'O')
        $o = 1;

    if($array[2] == 'O' and $array[4] == 'O' and $array[6] == 'O')
        $o = 1;

    if($array[0] == 'O' and $array[3] == 'O' and $array[6] == 'O')
        $o = 1;

    if($array[1] == 'O' and $array[4] == 'O' and $array[7] == 'O')
        $o = 1;

    if($array[2] == 'O' and $array[5] == 'O' and $array[8] == 'O')
        $o = 1;

    if($isfinished == 0)
        $isfinished = 1;

for($i = 0; $i < 9; $i ++)
    if($array[$i] == "-")
        $isfinished = 0;

    if($x == 1)
        echo 'x';
    else if ($o == 1){
        echo $index.'o';
    }
    else if($isfinished == 1){
        echo $index.'t';
    }
    else echo $index;