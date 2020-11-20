function isFileImage(input) {
    console.log(input);
    let file = input.files[0];
    console.log(file && file['type'].split('/')[0] === 'image');
    return file && file['type'].split('/')[0] === 'image';
}