from django.db import models
from . import Animal
# from . import Event


class User(models.Model):
    nickname = models.CharField(max_length=20)
    password = models.CharField(max_length=20)

    description = models.TextField()
    photo = models.CharField(max_length=50)
    animal = models.ForeignKey(Animal, on_delete=models.CASCADE)

    mail = models.CharField(max_length=20)
    phone_number = models.CharField(max_length=15)
    # https://stackoverflow.com/questions/19130942/whats-the-best-way-to-store-phone-number-in-django-models
    # events = models.ForeignKey(Event, on_delete=models.CASCADE)

    def __str__(self):
        return self.nickname

    def create_event(self):
        pass
