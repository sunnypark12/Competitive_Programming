#include <iostream>
#include <unordered_map>
#include <cstdlib>
#include <ctime>

int main() {
    std::ios::sync_with_stdio(false); 
    std::cin.tie(nullptr);
    
    int N;
    std::cin >> N;
    
    std::srand(std::time(nullptr));
    
    std::unordered_map<int, int> valueToIndexB; 
    int maxGuesses = 10000;
    for (int guess = 1; guess <= maxGuesses; ++guess) {
        int i = std::rand() % N + 1;
        int j = std::rand() % N + 1;
        
        std::cout << i << " " << j << std::endl;
        std::cout.flush();
        
        int Ai, Bj;
        std::cin >> Ai >> Bj;
        
        if (Ai == Bj) {
            return 0;
        }
        
        valueToIndexB[Bj] = j;
        
        if (valueToIndexB.count(Ai)) {
            std::cout << i << " " << valueToIndexB[Ai] << std::endl;
            std::cout.flush();
            return 0; 
        }
    }

    return 0;
}
