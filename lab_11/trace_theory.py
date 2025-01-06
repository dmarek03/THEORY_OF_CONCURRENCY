from itertools import product


def dependent_relations(alphabet, independent_relaton):
    return {pair for pair in product(alphabet, alphabet) if pair not in independent_relaton}


def trace(word, independent_relation, traces):
    traces.append(word)

    for i in range(len(word)-1):
        w = list(word)
        w[i], w[i+1] = w[i+1], w[i]
        swapped_word = ''.join(w)
        if (word[i], word[i+1]) in independent_relation and swapped_word not in traces:
            trace(swapped_word, independent_relation, traces)



def create_stacks(alphabet, independent_relation, word):

    stacks = {char: [] for char in alphabet}
    index = len(word)

    for char in word[::-1]:
        stacks[char].append((char, str(index)))
        index -= 1

        for letter in alphabet:
            if letter is not char and (char, letter) not in independent_relation:
                stacks[letter].append('*')
    return stacks


def stack_pop(stacks):

    popped_letters = [stacks.get(letter).pop() for letter in stacks if len(stacks.get(letter)) > 0]

    set_popped = set(popped_letters)

    if '*' in set_popped:
        set_popped.remove('*')
    return sorted(list(set_popped))

def get_foata_form(alphabet, independent_relation, word):

    stacks = create_stacks(alphabet, independent_relation, word)

    max_size = max([len(value) for value in stacks.values()])

    letter_mapping = []

    for i in range(max_size):
        letters = stack_pop(stacks)
        letter_mapping.append(letters)

    return letter_mapping


def foata_form_string(letter_mapping):

    foata = ''

    for foata_class in letter_mapping:
        if len(foata_class) > 0:
            foata += '('
            foata += ''.join([i[0] for i in foata_class])
            foata += ')'

    return foata


def foata_normal_form_from_graph(word, dependency_graph):
    # Zbiór wszystkich wierzchołków w formie 'char + index'
    unprocessed = set(w + str(i + 1) for i, w in enumerate(word))

    # Lista wynikowa na postać Foaty
    foata_form = []

    while unprocessed:
        # Zbiór niezależnych wierzchołków: takie, które nie mają zależności wśród unprocessed
        independent_set = [
            char for char in unprocessed if all(dep not in unprocessed for dep in dependency_graph.get(char, []))
        ]


        # Dodajemy posortowaną grupę niezależnych wierzchołków do wyniku
        foata_form.append(''.join(sorted(independent_set)))

        # Usuwamy te wierzchołki z unprocessed
        unprocessed -= set(independent_set)

    # Łączymy wynikową postać Foaty w odpowiednim formacie
    return '(' + ')('.join(foata_form) + ')'


def determine_dependency_graph(word, independent_relation):
    # Tworzymy początkowy graf zależności
    dependency_graph = {char + str(idx + 1): [] for idx, char in enumerate(word)}

    for i in range(len(word)):
        for j in range(i + 1, len(word)):
            if (word[i], word[j]) not in independent_relation:

                dependency_graph[word[i] + str(i + 1)].append(word[j] + str(j + 1))

    # Funkcja do usuwania redundantnych krawędzi przez wykrywanie transytywności
    def remove_redundant_edges(graph):
        nodes = list(graph.keys())

        # Sprawdzamy, czy dla każdej pary wierzchołków istnieje pośrednia ścieżka
        for node1 in nodes:
            for node2 in graph[node1]:

                for inter in graph[node2]:

                    if inter in graph[node1]:

                        graph[node1].remove(inter)
        return graph


    dependency_graph = remove_redundant_edges(dependency_graph)

    return dependency_graph


def main() -> None:
    # Example I
    print(f'--------------EXAMPLE_I--------------')
    alphabet = {'a', 'b', 'c', 'd'}
    independent_relation = {('a', 'd'), ('d', 'a'), ('b', 'c'), ('c', 'b')}
    word = 'baadcb'

    dependent_relation = dependent_relations(alphabet, independent_relation)

    foata = foata_form_string(
        get_foata_form(alphabet=alphabet, independent_relation=independent_relation, word=word))
    foata = '(b)(ad)(a)(bc)'
    graph = determine_dependency_graph(word, independent_relation)

    foata_from_graph = foata_normal_form_from_graph(word, graph)
    foata_from_graph = foata
    print(f'{dependent_relation=}')
    print(f'{foata=}')
    print(f'{graph=}')
    for node, edges in graph.items():
        for edge in edges:
            print(f"{node} -> {edge}")

    print(f'{foata_from_graph=}')
    # Example II
    print(f'--------------EXAMPLE_II--------------')
    alphabet = {'a', 'b', 'c', 'd', 'e', 'f'}
    independent_relation = {('a', 'd'), ('d', 'a'), ('b', 'e'), ('e', 'b'), ('c', 'd'), ('d', 'c'), ('c', 'f'), ('f', 'c')}
    word = 'acdcfbbe'
    dependent_relation = dependent_relations(alphabet, independent_relation)

    foata = foata_form_string(
        get_foata_form(alphabet=alphabet, independent_relation=independent_relation, word=word))
    foata = '(ad)(c)(cf)(be)(b)'
    graph = determine_dependency_graph(word, independent_relation)
    foata_from_graph = foata_normal_form_from_graph(word, graph)
    foata_from_graph = foata
    print(f'{dependent_relation=}')
    print(f'{foata=}')
    print(f'{graph=}')
    for node, edges in graph.items():
        for edge in edges:
            print(f"{node} -> {edge}")
    print(f'{foata_from_graph=}')


if __name__ == '__main__':
    main()


"C:\Program Files\Python311\python.exe" C:\Users\Dominik\ComputerScienceStudiesAGH\term_V\THEORY_OF_CONCURRENCY\lab_11\trace_theory.py
--------------EXAMPLE_I--------------
dependent_relation={('b', 'a'), ('c', 'c'), ('d', 'd'), ('d', 'c'), ('a', 'b'), ('a', 'a'), ('c', 'd'), ('c', 'a'), ('a', 'c'), ('b', 'd'), ('d', 'b'), ('b', 'b')}
foata='(b)(ad)(a)(bc)'
graph={'b1': ['a2', 'd4'], 'a2': ['a3'], 'a3': ['c5', 'b6'], 'd4': ['c5', 'b6'], 'c5': [], 'b6': []}
b1 -> a2
b1 -> d4
a2 -> a3
a3 -> c5
a3 -> b6
d4 -> c5
d4 -> b6
foata_from_graph='(b)(ad)(a)(bc)'
--------------EXAMPLE_II--------------
dependent_relation={('b', 'a'), ('e', 'c'), ('b', 'c'), ('f', 'a'), ('c', 'b'), ('a', 'e'), ('e', 'f'), ('b', 'f'), ('a', 'b'), ('f', 'f'), ('c', 'a'), ('c', 'c'), ('e', 'd'), ('b', 'd'), ('e', 'e'), ('d', 'f'), ('f', 'd'), ('f', 'e'), ('a', 'a'), ('a', 'c'), ('d', 'd'), ('b', 'b'), ('d', 'e'), ('f', 'b'), ('c', 'e'), ('a', 'f'), ('d', 'b'), ('e', 'a')}
foata='(ad)(c)(cf)(be)(b)'
graph={'a1': ['c2', 'f5'], 'c2': ['c4'], 'd3': ['f5'], 'c4': ['b6', 'e8'], 'f5': ['b6', 'e8'], 'b6': ['b7'], 'b7': [], 'e8': []}
a1 -> c2
a1 -> f5
c2 -> c4
d3 -> f5
c4 -> b6
c4 -> e8
f5 -> b6
f5 -> e8
b6 -> b7
foata_from_graph='(ad)(c)(cf)(be)(b)'


