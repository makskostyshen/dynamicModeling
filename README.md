# Моделювання стану неповно спостережуваних просторово розподілених динамічних систем

## Опим програми
Представлена нижче програма дозволяє моделювати функцію стану просторово розподіленої динамічної системи в певній області 
з дискретними спостереженнями у вигляді неперервної моделюючої функції. Динаміку спостреження описано лінійним диференціальним оператором, якому відповідає своя функція Гріна.


Програма за допомогю введених лінійного диференціального оператора $L(\partial_{x})$ , функції гріна G(s) та набору дискретних спостережень моделює функцію стану просторово розподіленої динамічної системи.

## Опис реалізації
Програма написана мовою Java і фреймворком Micronaut, веб-частина використовує Rocker Template Engine i Bootstrap. Результати обчислюються за допомогою мови Python і бібліотек Scipy, Numpy, Sympy, Matploplib.
Після уведення даних на інтерфейсі, Python файлом виконується обчислення. Результуючий графік зберігається у папці, її назва - відповідний час запуску програми.  

## Робота програми на прикладі

$S=(x,t) \in S_{0}^{T} = S_{0} \times [0,T]$ 

$S_{0}^{T} = ${ $(x,t): x \in [1,3], t \in [0,3]$ }

$L(\partial_{x}) = \partial_{t}-k\partial_{x}^{2}$

$G(s)=\frac{H(t)}{(4{\pi}kt)^{0.5}}exp(-\frac{x^{n}}{4kt})$

Введення моделі, функції Гріна, функції стану та розмірності:
![model_choose](https://github.com/makskostyshen/dynamicModeling/assets/82410925/23b5b9c4-6f8c-444f-8c83-d15f1aafcec4)

Введення спостережень:
![observations_choose](https://github.com/makskostyshen/dynamicModeling/assets/82410925/505eb7c5-b87b-496e-8f29-f68fd4bf97ec)

### Демонастрація розв'язку:
![res1](https://github.com/makskostyshen/dynamicModeling/assets/82410925/b0f49c5e-2477-4487-a069-154752b24dc4)

![res3](https://github.com/makskostyshen/dynamicModeling/assets/82410925/906780fc-33d9-4613-9e74-18a3719e84d2)

![res4](https://github.com/makskostyshen/dynamicModeling/assets/82410925/a98ab352-7072-493c-bc65-8510a012752f)

![res5](https://github.com/makskostyshen/dynamicModeling/assets/82410925/01f63e66-7860-4fda-9e1c-3b092d7b8a43)
