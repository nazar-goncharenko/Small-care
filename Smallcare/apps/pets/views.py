from django.shortcuts import render
from django.http import response, HttpResponse
from django.shortcuts import render
from .models import Animal


def index(request):
    return render(request, 'pets/index.html')


def test(request):
    return render(request, 'pets/test.html')


def my(request):
    list_of_pets = Animal.Animal.objects.all()
    return render(request, 'list.html', {'list_of_pets': list_of_pets})
