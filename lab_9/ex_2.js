const walkdir = require("walkdir");
const fs = require("fs");
const asyncLib = require("async");

let globalCount = 0;


function countLines(file, cb)  {
    let fileCount = 0;
    fs.createReadStream(file)
        .on("data", function (chunk) {
            const add = chunk.toString("utf8").split(/\r\n|[\n\r\u0085\u2028\u2029]/g).length - 1;
            fileCount += add;
        })
        .on("end", function () {
            globalCount += fileCount;
            console.log(`${file}: ${fileCount} lines`);
            cb();
        })
        .on("error", function (err) {
            console.error(`Error reading file ${file}:`, err);
            cb();
        });
};


async function processFilesSync(tasks) {
    let start = new Date().getTime();

    asyncLib.waterfall(tasks, () => {
        //console.log('Synchronous processing:')
        console.log(`Total lines: ${globalCount}`);
        var end = new Date().getTime();
        console.log(`Sync execution time: ${end - start} milliseconds`);
    });
}


async function processFileAsync(tasks){
    let start = new Date().getTime();
    await asyncLib.parallel(tasks, () => {
        //console.log('ASynchronous processing:')
        console.log(`Total lines: ${globalCount}`);
        let end = new Date().getTime();
        console.log(`Async execution time: ${end - start} milliseconds`);
    });
}


async function main(){
    const paths = walkdir.sync("./PAM08");
    const tasks = paths.map(path => cb => countLines(path, cb));


    //await processFilesSync(tasks)


    await processFileAsync(tasks)

}

main()
