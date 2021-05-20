# Project

Uppgiften gick ut på att skapa en app som hämtar data ifrån en XML-fil och parse med JSON.
Datan skall ritas ut på skärmen och det ska finnas en detaljvy som visar upp mer information om datan som hämtats.
Den datan som skapats är olika frågor som indelats i olika kategorier och områden. Varje quiz har ett namn och alla har olika mängd frågor och svar.
Datan hämtas med hjälp av hjälpbiblioteket Gson. Varje data fält lagras i en motsvarande variabel i en klass. Svaren kommer ligga i olika material cards.
I klassen RecyclerViewItem läggs läggs all data till från JSON-objektet. I Auxdata klassen läggs alla frågor och svar.

Eftersom alla svar läggs i en RecyclerView var det speciellt att kunna styra hur varje element bör reagera på ett klick. Eftersom alla svar läggs till dynamiskt från en array som skapats utifrån JSON-datan som hämtats. Användaren ska klicka på ett svar och det ska ge en indikation på vad som är rätt. Det var svårt att göra via en RecyclerView eftersom allting ritas ut samtidigt och det måste ske i klassen RecyclerViewAdapter och styra här vad som ska hända med med alla objekt inuti och styra varje objekt. Det krävs att man sätter vad som ska hända med alla objekt i klassen. T ex att det inte ska gå att klicka om man klickat en gång på ett svar. Det var svårt att veta hur RecyclerViewAdapter fungerade och vilka funktioner som påverkade vilka objekt. Men lärde att det går styra alla objekt i en funktion som heter 

```Java
@Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.title.setText(items.get(position).getChoice());
        if(isClickAble){
            holder.card.setOnClickListener(null);
        }
    }

    public void updateClickAble(boolean newValue){
        isClickAble= newValue;
    }

```
Funktionerna styr alla objekt i adapten och därför sätts alla objekt till oklickbara genom onBindViewHolder om man satt isClickAble till false i programkoden vid ett event, i det här fallet när man tryckt på ett svar. Här får man mer kontroll över det som ligger i listan att kunna styra vad som ska hända med varje objekt till skillnad mot en vanlig ListView. Här kan man enkelt säga vilken data från JSON ska placeras var i de olika beståndsdelar som existerar i en view. T ex här en materialcardView och sätter texten till de olika svaren som hämtats från datan. På så sätt kunna skapa ett quiz. Man kan sätta en eventlyssnare på varje vy som skapats och bestämma vad som ska hända om man klickar t ex respektive rätt eller fel svar kan man sätta grön eller röd färg beroende på svar. Eftersom varje knapp returnerar true eller false när man klickat på respektive knapp.




