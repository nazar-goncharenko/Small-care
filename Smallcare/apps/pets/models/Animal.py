from django.db import models
from .User import User


class Animal(models.Model):
    name = models.CharField(max_length=30)
    photo = models.CharField(max_length=50)
    description = models.CharField(max_length=400)

    user_id = models.ForeignKey(User, on_delete=models.CASCADE)

    def __str__(self):
        return self.name
