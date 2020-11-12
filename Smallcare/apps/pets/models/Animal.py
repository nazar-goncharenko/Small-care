from django.db import models


class Animal(models):
    name = models.CharField(max_length=30)
    photo = models.CharField(max_length=50)
    description = models.CharField(max_length=400)
