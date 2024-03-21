########################################################
########################################################
########################################################
########################################################
########################################################
########################################################
#Laborator 4
fileID = fopen('F:\e) Aplicatii\GNU Octave\fisiere\keywords_ham.txt','r');
vector_ham=textscan(fileID,'%s');
fclose(fileID);
fileID = fopen('F:\e) Aplicatii\GNU Octave\fisiere\keywords_spam.txt','r');
vector_spam=textscan(fileID,'%s');
fclose(fileID);

#Problema 1a)
nr_cuvinte_spam=rows(vector_spam{1})
nr_cuvinte_ham=rows(vector_ham{1})
nr_total_cuvinte=nr_cuvinte_ham+nr_cuvinte_spam;
probabilitate_email_spam=nr_cuvinte_spam/nr_total_cuvinte
probabilitate_email_ham=nr_cuvinte_ham/nr_total_cuvinte

#b)
v_ham=unique(vector_ham{1});
v_spam=unique(vector_spam{1});

mail1=strsplit("invite your friend today to click here"," ")
produs1=1;
for i=1:rows(v_spam);
  #i=cuvantul curent
  cnt=0;
  cuv1=v_spam{i};
  for j=1:rows(vector_spam{1})
    #numar de cate ori apare cuvantul i
    cuv2=vector_spam{1}{j};
    if strcmp(cuv1,cuv2)==1
      cnt++;
    endif
  endfor
  if ismember(cuv1,mail1)
    atribut_cuvant_spam=cnt/nr_cuvinte_spam;
  else
    atribut_cuvant_spam=1-cnt/nr_cuvinte_spam;
  endif
    produs1=produs1*atribut_cuvant_spam;
endfor



produs2=1;
for i=1:rows(v_ham);
  #i=cuvantul curent
  cnt=0;
  cuv1=v_ham{i};
  for j=1:rows(vector_ham{1})
    #numar de cate ori apare cuvantul i
    cuv2=vector_ham{1}{j};
    if strcmp(cuv1,cuv2)==1
      cnt++;
    endif
  endfor
  if ismember(cuv1,mail1)
    atribut_cuvant_ham=cnt/nr_cuvinte_ham;
  else
    atribut_cuvant_ham=1-cnt/nr_cuvinte_ham;
  endif
    produs2=produs2*atribut_cuvant_ham;
endfor





formula1=produs1*probabilitate_email_spam
formula2=produs2*probabilitate_email_ham
if formula1>formula2
  disp('email spam')
else
  disp('email ham')
endif





#Problema 3)
function out =lab4_3()
  n=1000;
  p=hygepdf(3:6,49,6,6);
  p=sum(p);
  x=geornd(p,1,n);
  prob_estimata=mean(x>=10)
  prob_teoretica=1-sum(geopdf(0:9,p))
endfunction







########################################################
########################################################
########################################################
########################################################
########################################################
########################################################
#Laborator 5
#Problema 1
hold on;
N=1000;


function result=randvardisc(x,p,N)
  %x=[1:4], p=[0.46,0.4,0.1,0.04],N=1000
  result=zeros(1,N);
  q=cumsum(p); %suma cumulativa a probabilitatilor;
  %q = [0.46, 0.86, 0.96, 1]
  %[0, 0.46, 0.86, 0.96, 1] ==> 4 intervale
  for i=1:N
    U=rand; %o valoare random in intervalul (0,1)
    j=1;
    while U>q(j) %se incrementeaza j pana se ajunge in intervalul dorit
      j++;
    endwhile
    result(i)=x(j); %se pune in vector ID-ul grupei sanguine
  endfor
endfunction




x1 = randsample([1:4],N,replacement = true, [0.46, 0.4, 0.1, 0.04]);
x2 = randvardisc([1:4], [0.46, 0.4, 0.1, 0.04],N);

frecventa_relativa1 = hist(x1, 1 : 4) / N;
bar(1 : 4, frecventa_relativa1, 'hist', 'FaceColor', 'b');

frecventa_relativa2 = hist(x2, 1 : 4) / N;
bar(1 : 4, frecventa_relativa2, 'FaceColor', 'r');






#Problema 2
N=1000;
rezultate_afis1=zeros(1,N);
for i=1:N
  y=rand;
  rezultate_afis1(i)=-12*log(1-y);
endfor
rezultate_afis2=exprnd(12,1,N);


disp("Valorile mean si std pentru primul array")
medie1=mean(rezultate_afis1)
std(rezultate_afis1)

disp("Valorile mean si std pentru al doilea array")
medie2=mean(rezultate_afis2)
std(rezultate_afis2)









#Problema 3
nr_sim = 100;
clf;
function [X,Y]=boxmuller(N)
  U=rand(2,N); %n perechi de numere aleatoare independente

  R=sqrt(-2*log(U(1,:))); %R(i)
  V=2*pi*U(2,:);          %V(i)

  X=R.*cos(V); %inmultire element cu element
  Y=R.*sin(V);
endfunction



t=linspace(0, 2 * pi, 360);
polar(t,4*ones(1,360),'w'); %grafic in coordonate polare
hold on; %se face dupa polar ca sa se pastreze coordonatele polare
[X,Y]=boxmuller(nr_sim);
plot(X,Y,'r*');

polar(t,0.5*ones(1,360),'b');

mean(sqrt(X.^2+Y.^2)<0.5);
1-exp(-1/8); %frecventa relativa teoretica

%frecventa replativa rep prob P(sqrt(X^2 + Y^2)<0.5)=P(sqrt(-2*log(U1))<0.5)=
%=P(-2*log(U1)<1/4)=P(log(U1)>-1/8)=P(U1>exp(-1/8))=1-P(U1<exp(-1/8))

Z=normrnd(0,1,2,nr_sim);
plot(Z(1,:),Z(2,:),'c*');
mean(sqrt(Z(1,:).^2+Z(2,:).^2)<0.5)







########################################################
########################################################
########################################################
########################################################
########################################################
########################################################
#Laborator 6
#Problema 1a)
nr_sim=1000;
X=normrnd(165,10,1,nr_sim);
#hist(X)


#b)
hold on;
minim=min(X)
maxim=max(X)
[x1,xHelp]=hist(X,10);
hist(X,xHelp,10/(maxim-minim))
Y=linspace(minim,maxim,1000);
plot(Y,normpdf(Y,165,10),'r')


#c)
val_medie=mean(X)
valori_interval=0;
for i=1:nr_sim
  if X(i)>=160 && X(i)<=170
    valori_interval++;
  endif
endfor
disp(valori_interval/nr_sim)
std(X)











#Problema 3)
N=1000; %numarul de simulari
r=rand(1, N);  %vector valori intre 0 si 1, util pentru gasirea implrimantei selectate
y=(r <= 0.4).*exprnd(5, 1, N)+(r > 0.4).*unifrnd(4, 6, 1, N) % vectorul cu timpul necesar printarii la fiecare simulare

#subpunctul a)
media=mean(y)
deviatia=std(y)


#subpunctul b)
nr=0;
for i=1:1000
  if(y(1,i)>5)
    nr++;
  endif
endfor
probabilitatea=nr/1000
#mean(y>5) sau se poate folosi asta






#subpunctul c)
%P(B | A) = P(B & A) / P(A)
sum((r>0.4) & (y>5))/sum(y>5)
mean((r>0.4) & (y>5))/mean(y>5)


countSecondPrinter=0;
countPrintingLonger=0;

for i=1:N
  if y(i)>5
	countPrintingLonger++;
	if r(i)>0.4
		countSecondPrinter++;
	endif
  endif
endfor

countSecondPrinter/countPrintingLonger  %sum este echivalent
(countSecondPrinter/N)/(countPrintingLonger/N)







