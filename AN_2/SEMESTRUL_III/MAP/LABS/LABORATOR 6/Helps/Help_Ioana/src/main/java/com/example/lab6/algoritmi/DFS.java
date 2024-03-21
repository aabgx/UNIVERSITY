package com.example.lab6.algoritmi;

import com.example.lab6.domain.*;

import java.util.ArrayList;
import java.util.List;

public class DFS extends Network {
    private final List<User> noduri;
    private final List<Friendship> muchii;
    private int id;
    private final List<Nod> structura;
    public DFS(List<Friendship>muchii, List<User> noduri)
    {
        structura = new ArrayList<>();
        this.noduri = noduri;
        this.muchii = muchii;
        id=0;
    }
    private void initializareStructura()
    {
        for(User e:this.noduri)
        {
            Nod nou=new Nod();
            nou.setColour("alb");
            nou.setValoare(e);
            nou.setId(id++);
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
                structura.get(v.getId()).setColour("gri");
                structura.get(v.getId()).setDistanta(structura.get(e.getId()).getDistanta() + 1);
                componenteRecursivitate(structura.get(v.getId()));
            }
        }
        structura.get(e.getId()).setColour("negru");
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
            copieMuchii.clear();
            copieMuchii.addAll(muchii);
            structura.get(i).setDistanta(0);
            if(structura.get(i).getColour().equals("alb"))
            {
                componenteRecursivitate(structura.get(i));
            }
            boolean conex = false;
            List<User> numeComponenta = new ArrayList<>();
            for(Nod s:structura)
            {
                if(s.getColour().equals("negru"))
                {
                    numeComponenta.add(s.getValoare());
                    s.setColour("albastru");
                    User nou2 = noduri.get(s.getId());
                    componenta.add(componenta.size(), nou2);
                    int size = copieMuchii.size();
                    for (int j = 0; j < size; j++)
                    {
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
                Community comunitate = new Community(componenta,conexa);
                super.componenteConexe.add(componenteConexe.size(),comunitate);
                super.number++;
                componenta.clear();
                conexa.clear();
            }
        }
    }
}
