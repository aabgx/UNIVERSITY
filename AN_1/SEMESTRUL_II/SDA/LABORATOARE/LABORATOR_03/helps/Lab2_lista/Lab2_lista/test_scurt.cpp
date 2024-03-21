#include "test_scurt.h"

#include <assert.h>
#include <exception>


#include "lista.h"
#include "iteratorLP.h"



using namespace std;

bool esteDivizibil_cu_3_test(TElem e)
{
    return e % 3 == 0;
}

void testAll() {
    Lista lista = Lista();
    assert(lista.dim() == 0);
    assert(lista.vida());

    lista.adaugaSfarsit(1);
    assert(lista.dim() == 1);
    assert(!lista.vida());

    IteratorLP it = lista.cauta(1);
    assert(it.valid());
    assert(it.element() == 1);
    it.urmator();
    assert(!it.valid());
    it.prim();
    assert(it.valid());
    assert(it.element() == 1);

    assert(lista.sterge(it) == 1);
    assert(lista.dim() == 0);
    assert(lista.vida());

    lista.adaugaInceput(1);
    assert(lista.dim() == 1);
    assert(!lista.vida());
    /*
    Lista lista_de_filtrat = Lista();
    lista_de_filtrat.adaugaSfarsit(1);
    lista_de_filtrat.adaugaInceput(3);
    lista_de_filtrat.adaugaSfarsit(5);
    lista_de_filtrat.adaugaSfarsit(6);
    lista_de_filtrat.adaugaSfarsit(12);
    assert(lista_de_filtrat.dim() == 5);
    lista_de_filtrat.filtreaza(esteDivizibil_cu_3_test);
    assert(lista_de_filtrat.dim() == 3);
    */
}

