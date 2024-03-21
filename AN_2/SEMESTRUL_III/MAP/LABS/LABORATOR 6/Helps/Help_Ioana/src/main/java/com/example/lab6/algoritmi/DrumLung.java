package com.example.lab6.algoritmi;

import com.example.lab6.domain.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DrumLung extends Network {
    private final List<User> noduri;
    private final List<Friendship> muchii;
    private int id;
    private final List<Nod> structura;
    private final List<Community> numePereche = new ArrayList<>();
    private List<List<Nod>> drumLung = new ArrayList<>();
    public DrumLung(List<Friendship>muchii, List<User> noduri)
    {
        structura = new ArrayList<>();
        this.noduri = noduri;
        this.muchii = muchii;
        id=0;
    }
    private void initializareStructura()
    {
        structura.clear();
        id=0;
        for(User e:this.noduri)
        {
            Nod nou=new Nod();
            nou.setColour("alb");
            nou.setValoare(e);
            nou.setId(id++);
            nou.setParinte(null);
            structura.add(structura.size(),nou);
        }
    }
    private List<Nod> listaAdiacenta(Nod e)
    {
        List<Nod> vecini = new ArrayList<>();
        for(Friendship f:muchii)
        {
            if(f.getU1().equals(e.getValoare()))
            {
                Nod nou = new Nod();
                for(Nod c : structura)
                {
                    if(c.getValoare().equals(f.getU2()))
                    {
                        nou.setId(c.getId());
                        nou.setColour(c.getColour());
                        nou.setValoare(f.getU2());
                        break;
                    }
                }
                vecini.add(vecini.size(),nou);
            }
            else
            {
                if(f.getU2().equals(e.getValoare()))
                {
                    Nod nou = new Nod();
                    for(Nod c:structura)
                    {
                        if(c.getValoare().equals(f.getU1()))
                        {
                            nou.setId(c.getId());
                            nou.setColour(c.getColour());
                            nou.setValoare(f.getU1());
                            break;
                        }
                    }
                    vecini.add(vecini.size(),nou);
                }
            }
        }
        return vecini;
    }
    private void componenteRecursivitate(Nod e)
    {
        structura.get(e.getId()).setColour("gri");
        List<Nod> vecini = listaAdiacenta(e);
        for (Nod v : vecini) {
            if (structura.get(v.getId()).getColour().equals("alb") && structura.get(e.getId()).getDistanta() + 1 > structura.get(v.getId()).getDistanta())
            {
                structura.get(v.getId()).setParinte(structura.get(e.getId()));
                structura.get(v.getId()).setColour("gri");
                structura.get(v.getId()).setDistanta(structura.get(e.getId()).getDistanta() + 1);
                componenteRecursivitate(structura.get(v.getId()));
            }
        }
        structura.get(e.getId()).setColour("negru");
    }
    public void getDrumMaximComponenta()
    {
        super.maxim = Collections.max(drumMaxim);
        int i;
        for(i=0;i<numePereche.size(); i++)
        {
            if(super.drumMaxim.get(i)==super.maxim)
            {
                super.sociabila = numePereche.get(i);
                super.secventaMax = drumLung.get(i);
                super.setSecventaMax(secventaMax);
                break;
            }
        }
    }
    public void ReteaSociabila(List<Nod> secventa,Nod inceput)
    {
        if(inceput!=null)
        {
            secventa.add(inceput);
            if(inceput.getDistanta()!=0)
            {
                ReteaSociabila(secventa,inceput.getParinte());
            }
        }
    }
    public void componenteConexe()
    {
        initializareStructura();
        List<User> componenta = new ArrayList<>();
        List<Friendship> conexa = new ArrayList<>();
        List<Friendship> copieMuchii = new ArrayList<>();
        int i;
        for(i=0; i<noduri.size(); i++)
        {
            structura.get(i).setDistanta(0);
            initializareStructura();
            copieMuchii.clear();
            copieMuchii.addAll(muchii);
            componenteRecursivitate(structura.get(i));
            boolean conex = false;
            List<User> numeComponenta = new ArrayList<>();
            List<Integer> drumMaximComponenta = new ArrayList<>();
            for(Nod n:structura) {
                drumMaximComponenta.add(n.getDistanta());
            }
            super.drumMaxim.add(Collections.max(drumMaximComponenta));
            List<Nod> drumAuxiliar = new ArrayList<>();
            for(Nod n:structura) {
                if(n.getDistanta()==drumMaxim.get(drumMaxim.size()-1))
                {
                    ReteaSociabila(drumAuxiliar,n);
                    break;
                }
            }
            for(Nod n:structura) {
                n.setDistanta(-1);
            }
            drumLung.add(drumAuxiliar);
            for(Nod s:structura)
            {
                if(s.getColour().equals("negru"))
                {
                    numeComponenta.add(s.getValoare());
                    User nou2 = noduri.get(s.getId());
                    componenta.add(componenta.size(), nou2);
                    int size = copieMuchii.size();
                    for (int j = 0; j < size; j++) {
                        if (copieMuchii.get(j).getU1().equals(s.getValoare()) || copieMuchii.get(j).getU2().equals(s.getValoare())) {
                            conexa.add(conexa.size(), copieMuchii.get(j));
                            copieMuchii.remove(copieMuchii.get(j));
                            size--;
                            j=-1;
                        }
                    }
                    conex = true;
                }
            }
            if(conex)
            {

                List<Friendship> conexaCopie = new ArrayList<>();
                conexaCopie.addAll(conexa);
                Community comunitate = new Community(componenta,conexa);
                super.componenteConexe.add(componenteConexe.size(),comunitate);
                numePereche.add(comunitate);
                componenta.clear();
                conexa.clear();
            }
        }
        copieMuchii.clear();
        this.getDrumMaximComponenta();
    }
}
