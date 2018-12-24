$a = gc 'C:\Users\user44\Downloads\DI.FM - Favorites (1).pls'
for ($i = 2; $i -lt $a.Count - 1; $i = $i + 3) {
    $url = $a[$i]
    $name = $a[$i + 1]

    if ($url -match "80\/(\w*)\?\d*") {
        $mediaid = $matches[1]
        if ($name -match "DI.FM - (.*)$") {
            $title = $matches[1]
            Write-Host "Channel(`"$mediaid`", `"$title`", R.drawable.techno),"
        }
    }
}


