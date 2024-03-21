########################################################
########################################################
########################################################
########################################################
########################################################
########################################################
#Laborator 1


1)x=[4,5,2,1]; perms(x)

2)x=[4,5,6,7,1,2]; nchoosek(x,3)

3)
function out=aranjamente(v,k)
%v este vectorul de numere
%k este numarul k cerut
len=length(v);
if(k>len)
	printf("Eroare, k nu poate fi mai mare ca n.\n")
	return;
end
matrix=nchoosek(v,k);
for step=1:len
	perms(matrix(step,:))
end
end






########################################################
########################################################
########################################################
########################################################
########################################################
########################################################
#Laborator 2

%randi(365,1,23)
##Problema 1
disp("Running")
function out=zi_nastere(n)
  persoane=23;
  cnt=0;
  for step=1:n
      zile_nastere=randi(365,1,23);
      zile_diferite=unique(zile_nastere);
      if length(zile_diferite)<persoane
        cnt++;
      endif
  endfor
  rezultat=cnt/n;
  disp(rezultat)
endfunction
zi_nastere(1000)


##Problema 2a)
pkg load statistics
rectangle('Position',[0 0 1 1])
hold on
axis square
axis off
cnt=0;
for step=1:500
  Ax=rand();
  Ay=rand();
  O=[0.5,0.5]; P=[Ax,Ay];
  if pdist([O;P])<0.5
    cnt++;
    plot([Ax],[Ay],'*r','MarkerSize',5)
  endif
endfor
disp(cnt/500)


##Problema 2b)
pkg load statistics
rectangle('Position',[0 0 1 1])
hold on
axis square
axis off
cnt=0;
for step=1:500
  Ax=rand();
  Ay=rand();
  O=[0.5,0.5]; P=[Ax,Ay];
  V1=[0,0]; V2=[0,1]; V3=[1,0]; V4=[1,1];
  centru=pdist([O;P]);
  dist1=pdist([P;V1]);
  dist2=pdist([P;V2]);
  dist3=pdist([P;V3]);
  dist4=pdist([P;V4]);
  if (centru<dist1 && centru<dist2 && centru<dist3 && centru<dist4)
    cnt++;
    plot([Ax],[Ay],'*r','MarkerSize',5)
  endif
endfor
disp(cnt/500)











########################################################
########################################################
########################################################
########################################################
########################################################
########################################################
#Laborator 3
#Problema 1a)
function out=bile1()
   bile="RRRRRAAAVV";
   cnt=0;
   for i=1:5000
     extragere=randsample(bile,3,replacement=false);
     if index(extragere,"R")!=0
       cnt++;
     endif
   endfor
   disp(cnt/5000)
endfunction


#Problema 1b)
function out=bile2()
   bile="RRRRRAAAVV";
   cnt=0;
   for i=1:5000
     extragere=randsample(bile,3,replacement=false);
     if index(extragere,"RRR")!=0
       cnt++;
     endif
   endfor
   disp(cnt/5000)
endfunction


#Problema 1d)
function out=bile4()
   bile="RRRRRAAAVV";
   cnt1=0;
   cnt2=0;
   for i=1:5000
     extragere=randsample(bile,3,replacement=false);
     if index(extragere,"R")!=0
       cnt1++;
     endif
     if index(extragere,"RRR")!=0
       cnt2++;
     endif
   endfor
endfunction












#Problema 2b)
function out=lab3_2b()
  clf; grid on; hold on;
  p=0.33; n=5; m=5000;
  cnt=0;
  x=binornd(n,p,1,m);
  for i=1:5000
    if x(i)==2
      cnt++;
    endif
  endfor
  disp(cnt/5000)
endfunction










#Problema 3a)
#INEFICIENT,NEFOLOSITOR
nr_sim=20;
x=[];
for i=1:nr_sim
  zaruri=randi(6,1,4);
  sum=zaruri(1)+zaruri(2)+zaruri(3)+zaruri(4);
  x(end+1)=sum;
endfor
y=unique(x);
m=zeros(2,size(y)(2));

for i=1:size(y)(2)
  frecv=0;
  for j=1:nr_sim
    if(x(j)==y(i))
      frecv++;
    endif
  endfor
  m(1,i)=y(i);
  m(2,i)=frecv;
endfor
disp(m)



#Problema 3a)
nr_sim=1000;
sume_posibile=4:24;
zaruri=randi(6,4,nr_sim);
%clear sum
sume_aparute=sum(zaruri);
frecvente=hist(sume_aparute, sume_posibile);
%hist(sume_aparute, sume_posibile) ->arata o histograma
matrice=[sume_posibile; frecvente];





#Problema 3b)
clf; grid; hold on;
xticks(sume_posibile); xlim([3, 25]);
yticks([0: 0.01 : 0.14]); ylim([0, 0.14]);
bar(sume_posibile,frecvente/nr_sim,'hist','FaceColor','b')





#Problema 3c)
#se foloseste in paralel cu 3b)
sume_teoretice=[];
for zar1=1:6
  for zar2=1:6
    for zar3=1:6
      for zar4=1:6
        sume_teoretice=[sume_teoretice,zar1+zar2+zar3+zar4];
      endfor
    endfor
  endfor
endfor
frecvente_t=hist(sume_teoretice,sume_posibile);
matrice2=[sume_posibile;frecvente_t];
#reprezentare grafica
xticks(sume_posibile); xlim([3, 25]);
yticks([0: 0.01 : 0.14]); ylim([0, 0.14]);
bar(sume_posibile,frecvente_t/6^4,'hist','FaceColor','r')

frecventa_maxima_t=max(frecvente_t)
most_frequent_th_sum = sume_posibile(frecvente_t == frecventa_maxima_t)
legend('frecvente relative', 'probabilitati');
set(findobj('type', 'patch'), 'facealpha', 0.7);



#Problema 3d)
prob_cond_simulata=sum((sume_aparute<=20) & (sume_aparute>=10))/sum(sume_aparute<=20)
prob_cond_teoretica=sum((sume_teoretice<=20) & (sume_teoretice>=10))/sum(sume_teoretice<=20)



