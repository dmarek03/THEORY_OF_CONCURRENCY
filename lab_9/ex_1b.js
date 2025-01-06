const waterfall = require('async/waterfall');

function printAsync(s, cb) {
    const delay = Math.floor((Math.random() * 1000) + 500);
    setTimeout(function () {
        console.log(s);
        if (cb) cb();
    }, delay);
}

function task(n) {
    return function (cb) { // Zwracamy funkcję, która spełnia wymagania `waterfall`
        printAsync(n, function () {
            console.log('task', n, 'done');
            cb();
        });
    };
}

function loop(m) {
    const tasks = [];

    // Tworzymy tablicę funkcji do przekazania do `waterfall`
    for (let i = 1; i <= m; i++) {
        tasks.push(task(i)); // Dodajemy funkcje `task` jako kroki
    }

    // Uruchamiamy sekwencję za pomocą `async.waterfall`
    waterfall(tasks, () => {
        console.log('done'); // Wywołanie po zakończeniu wszystkich zadań
    });
}

loop(4);
