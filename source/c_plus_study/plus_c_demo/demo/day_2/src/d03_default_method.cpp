//
// Created by Zero on 2019/10/12.
//

#include <iostream>
using namespace std;

void myPrint(int x = 3)
{
    cout<<"x"<<x<<endl;
}

//1 若 你填写参数,使用你填写的,不填写默认
//2 在默认参数规则 ，如果默认参数出现，那么右边的都必须有默认参数
void myPrint2( int m, int n, int x = 3, int y = 4)
//void myPrint2( int m, int n, int x = 3, int y )
{
    cout<<"x"<<x<<endl;
}

int main31(){
    //TODO: 默认参数
    cout<<"默认参数"<<endl;
    myPrint(4);
    myPrint();
    return 0;
}

//函数占位参数 函数调用是,必须写够参数

void func1(int a, int b, int)
{
    cout<<"a"<<a<<" b"<<b<<endl;
}

int main32(){
    //TODO: 函数占位参数
    cout<<"函数占位参数"<<endl;
//func1(1, 2); //err调用不起来
    func1(1, 2, 3);
    return 0;
}
//默认参数和占位参数

void  func2(int a, int b, int =0)
{
    cout<<"a="<<a<<";b="<<b<<endl;
}

int main33()
{
    func2(1, 2); //0k
    func2(1, 2, 3); //ok

    cout<<"hello..."<<endl;
    return 0;
}
