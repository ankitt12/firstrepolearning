#include <bits/stdc++.h>
#include <jni.h>
#include "JNITest.h"
using namespace std;
JNIEXPORT void JNICALL Java_JNITest_greet
	(JNIEnv *env, jobject obj){
	cout << "..Welcome to the world of JNI..\n";
	cout << "Scientific Calculator\n";
	cout << "1.Sin function\n";
	cout << "2.Cosine function\n";
	cout << "3.Tan function\n";
	cout << "4.Power\n";
	cout << "5.Exponent\n";
	cout << "6.Division\n";
	cout << "7.Exit\n";	
	int choice, angle, num, power, base, exp1;	
	float num1, num2, ans;
	//cout << atan(1)*4;
	while(true){
		cout << "\nEnter choice";
		cin >> choice;
		switch(choice){
		case 1:
			cout << "Enter angle in radians\n";
			cin >> angle;
			cout << sin(angle) ;
			break;
		case 2:
			cout << "Enter angle in radians\n";
			cin >> angle;
			cout << cos(angle);
			break;
		case 3:
			cout << "Enter angle in radians\n";
			cin >> angle;
			cout << tan(angle);
			break;
		case 4:
			cout << "Enter the base and exp\n";
			cin >> base >> exp1;
			cout << pow(base,exp1);
			break;
		case 5:
			cout << "Enter the number(e^number)\n";
			cin >> num;
			cout << exp(num);
			break;
		case 6:
			cout << "Enter num1 and num 2\n";
			cin >> num1 >> num2;
			if(num2 == 0){
				cout << "Division By Zero Error\n";	
				break;		
			}else{
				cout << num1/num2;
			}
			break;
		}
		if (choice == 7){
			cout << "Exit\n";
			break;
		}
				
	}
	
	return;
}
