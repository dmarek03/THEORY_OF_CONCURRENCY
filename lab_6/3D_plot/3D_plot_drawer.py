import numpy as np
import pandas as pd
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import Axes3D

# Załaduj dane z pliku CSV
data = pd.read_csv('data/output.csv')

# Przygotuj dane do wykresu
# Oś X: liczba czytelników
# Oś Y: liczba pisarzy
# Oś Z: średni czas oczekiwania (średnia z czasu oczekiwania czytelników i pisarzy)
X = data['readersAmount'].values
Y = data['writersAmount'].values
Z = (data['readerAvgWaitTime'] + data['writerAvgWaitTime']) / 2  # Średni czas oczekiwania

# Stwórz siatkę X, Y
X_unique = np.unique(X)
Y_unique = np.unique(Y)
X_grid, Y_grid = np.meshgrid(X_unique, Y_unique)

# Przygotowanie siatki Z, która będzie odpowiadała czasowi oczekiwania
Z_grid = np.zeros(X_grid.shape)

# Przypisanie wartości Z na podstawie danych
for i, x_val in enumerate(X_unique):
    for j, y_val in enumerate(Y_unique):
        # Znajdź odpowiednią wartość Z dla każdej kombinacji X, Y
        mask = (X == x_val) & (Y == y_val)
        if mask.any():
            Z_grid[j, i] = np.mean(Z[mask])  # Średnia wartość dla tej kombinacji

# Tworzenie wykresu 3D
fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')

# Rysowanie powierzchni
surf = ax.plot_surface(X_grid, Y_grid, Z_grid, cmap='viridis')

# Dodanie etykiet osi
ax.set_xlabel('Liczba czytelników')
ax.set_ylabel('Liczba pisarzy')
ax.set_zlabel('Średni czas oczekiwania (ms)')

# Dodanie kolorowego paska (legendy)
fig.colorbar(surf)

# Pokaż wykres
plt.show()

