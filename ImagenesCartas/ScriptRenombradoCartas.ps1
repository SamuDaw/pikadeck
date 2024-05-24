# Define la ruta de la carpeta que contiene los archivos
$folderPath = "C:\Users\Samuel\OneDrive\2_DAW\POYECTO FINAL\proyecto-m-dulo-pwr-abraham-y-samuel\ImagenesCartas\Gen1"

# Obtén todos los archivos .png en la carpeta
$files = Get-ChildItem -Path $folderPath -Filter "*.png"

# Recorre cada archivo y renómbralo
foreach ($file in $files) {
    # Extrae la primera palabra del nombre del archivo (sin extensión)
    $newName = ($file.BaseName -split '-')[0] + ".png"
    
    # Construye la ruta completa del nuevo nombre
    $newPath = Join-Path -Path $folderPath -ChildPath $newName
    
    # Renombra el archivo
    Rename-Item -Path $file.FullName -NewName $newPath
}

Write-Host "Renombrado completo."