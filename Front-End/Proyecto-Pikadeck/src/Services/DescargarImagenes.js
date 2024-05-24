// Función para descargar una imagen
function downloadImage(url, filename) {
    fetch(url)
        .then(response => response.blob())
        .then(blob => {
            const a = document.createElement('a');
            const url = window.URL.createObjectURL(blob);
            a.href = url;
            a.download = filename;
            document.body.appendChild(a);
            a.click();
            setTimeout(() => {
                document.body.removeChild(a);
                window.URL.revokeObjectURL(url);
            }, 100);
        })
        .catch(console.error);
}


const spanElements = document.querySelectorAll('.grid-image');
console.log(spanElements);
spanElements.forEach((span, index) => {

    const imgTags = span.querySelectorAll('img');
    const filename = `${span.parentElement.href.split('/').pop()}.jpg`;

    console.log(`Descargando ${imgTags.length} imágenes...`);
    imgTags.forEach((img, imgIndex) => {
        const imgUrl = img.src;
            downloadImage(imgUrl, filename);
        });
    });

console.log('Descarga completada.');